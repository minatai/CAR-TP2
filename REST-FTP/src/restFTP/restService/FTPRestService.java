package restFTP.restService;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.util.Base64;

import restFTP.model.ItemBuilder;
import restFTP.service.FTPService;

/**
 * This class handle every thing related with the ftp API.
 *
 * @author arctarus
 *
 */
@Path("/ftp")
public class FTPRestService {

	/**
	 * The tools used to connect to the FTP server.
	 */
	private static FTPService ftpService = FTPService.getInstance();

	/**
	 * Decode the HTTP header authorization.
	 *
	 * @param authorization
	 *            the content of the header
	 * @return an array with in the login at index 0 and the password at index
	 *         1.
	 */
	private String[] decodeAuthHeader(final String authorization) {
		final String base64 = authorization.replace("Basic ", "");
		final String decoded = new String(Base64.decodeBase64(base64));
		return decoded.split(":");
	}

	/**
	 * If necessary, connect to the FTP server and log in with the credentials
	 * set in the class Started
	 *
	 * @param authorization
	 *            the content of the HTTP header authorization
	 * @return true if the sequence successfully completed. False otherwise.
	 */
	private boolean connectAndLogin(final String authorization) {
		if (authorization == null) {
			return false;
		}

		final String auth[] = this.decodeAuthHeader(authorization);
		if (!FTPRestService.ftpService.isConnected()) {
			if (FTPRestService.ftpService.connect()) {
				return FTPRestService.ftpService.login(auth[0], auth[1]);
			}
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Create a new directory. Does not create the parent directory if needed
	 *
	 * @param dirName
	 *            the name of the directory to create
	 * @param authorization
	 *            the content of the HTTP header authorization
	 * @return a string to send to the client who represent the result of this
	 *         operation
	 */
	@POST
	@Path("/folder/{name: .+}")
	public Response createDirectory(
			@PathParam(value = "name") final String dirName,
			@HeaderParam("Authorization") final String authorization) {
		if (this.connectAndLogin(authorization)) {
			if (FTPRestService.ftpService.createDirectory(dirName)) {
				return Response.ok().build();
			}
			return Response.status(Status.FORBIDDEN)
					.entity("The file is not created.").build();
		} else {
			return Response.status(Status.UNAUTHORIZED)
					.header("WWW-Authenticate", "Basic realm=\"localhost\"")
					.entity("Impossible to connect or log in").build();
		}
	}

	/**
	 *
	 * Create a new file.
	 *
	 * @param remote
	 *            the name of the new file. If this file name contains a
	 *            directory name, create the new file in.
	 * @param fileInStream
	 *            the InputStream of the new file
	 * @param authorization
	 *            the content of the HTTP header authorization
	 * @return a string to send to the client who represent the result of this
	 *         operation
	 */
	@POST
	@Path("/file/{name: .*}")
	public Response createFile(@PathParam(value = "name") final String remote,
			final InputStream fileInStream,
			@HeaderParam("Authorization") final String authorization) {
		if (this.connectAndLogin(authorization)) {
			if (FTPRestService.ftpService.createFile(remote, fileInStream)) {
				return Response.ok().build();
			} else {
				return Response.status(Status.FORBIDDEN)
						.entity("The file is not created.").build();
			}
		}
		return Response.status(Status.UNAUTHORIZED)
				.header("WWW-Authenticate", "Basic realm=\"localhost\"")
				.entity("Impossible to connect or log in").build();
	}

	/**
	 *
	 * @param authorization
	 *            the content of the HTTP header authorization
	 */
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/folder/{name: .*}")
	public Response listDirectory(
			@PathParam(value = "name") final String dirName,
			@HeaderParam("Authorization") final String authorization) {

		if (this.connectAndLogin(authorization)) {
			final FTPFile[] res = FTPRestService.ftpService
					.listDirectory(dirName);
			final ItemBuilder listBuilder = new ItemBuilder();
			final String list = listBuilder.buildList(dirName, res);
			return Response.ok(list, MediaType.TEXT_HTML).build();

		} else {
			return Response.status(Status.UNAUTHORIZED)
					.header("WWW-Authenticate", "Basic realm=\"localhost\"")
					.entity("Impossible to connect or log in").build();
		}

	}

	/**
	 *
	 * @param fileName
	 * @param authorization
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	@Path("/file/{name: .+}")
	public Response getFile(@PathParam(value = "name") final String fileName,
			@HeaderParam("Authorization") final String authorization) {

		if (this.connectAndLogin(authorization)) {

			return FTPRestService.ftpService.getFile(fileName);
		} else {
			return Response.status(Status.UNAUTHORIZED)
					.header("WWW-Authenticate", "Basic realm=\"localhost\"")
					.entity("Impossible to connect or log in").build();
		}

	}

	/**
	 * Delete the given directory/file.
	 *
	 * @param name
	 *            the directory/file
	 * @param authorization
	 *            the content of the HTTP header authorization
	 * @return True if the deletion is successful. False, if the directory
	 *         contains some files or subdirectories, or it does not exists.
	 */
	@DELETE
	@Path("/delete/{folder: .+}")
	public Response deleteFileOrDirectory(
			@PathParam(value = "folder") final String name,
			@HeaderParam("Authorization") final String authorization) {
		Response response = null;
		if (this.connectAndLogin(authorization)) {
			if (FTPRestService.ftpService.delete(name)) {
				System.out.printf("Deletion of %s successfull\n", name);
				response = Response.ok().build();
			} else {
				response = Response
						.status(Status.FORBIDDEN)
						.entity("Impossible to delete the given directory/file")
						.build();
			}
		} else {
			response = Response.status(Status.UNAUTHORIZED)
					.header("WWW-Authenticate", "Basic realm=\"localhost\"")
					.entity("You are not authorized to execute this request.")
					.build();
		}
		return response;
	}

	@PUT
	@Path("/file/{file: .+}")
	public Response putFile(@PathParam(value = "file") final String remote,
			final InputStream fileInStream,
			@HeaderParam("Authorization") final String authorization) {

		if (this.connectAndLogin(authorization)) {

			try {
				return FTPRestService.ftpService.putFile(remote, fileInStream);
			} catch (final IOException e) {
				return Response.status(Status.FORBIDDEN)
						.entity("Directory inaccessible").build();
			}
		} else {
			return Response.status(Status.UNAUTHORIZED)
					.header("WWW-Authenticate", "Basic realm=\"localhost\"")
					.entity("Impossible to connect or log in").build();
		}

	}
}
