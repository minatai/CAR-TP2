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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.util.Base64;

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
		final String base64 = authorization.replace("Basic ", "");
		final String decoded = new String(Base64.decodeBase64(base64));

		final String auth[] = decoded.split(":");
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
		System.out.println("Header authorizaion " + authorization);
		if (this.connectAndLogin(authorization)) {
			if (FTPRestService.ftpService.createDirectory(dirName)) {
				return Response.ok().build();
			}
			return Response.status(Status.FORBIDDEN)
					.entity("The file is not created.").build();
		} else {
			return Response.status(Status.UNAUTHORIZED)
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
				.entity("Impossible to connect or log in").build();
	}

	/**
	 *
	 * @param authorization
	 *            the content of the HTTP header authorization
	 */
	@GET
	@Path("/folder/{name: .*}")
	public Response listDirectory(
			@PathParam(value = "name") final String dirName,
			@HeaderParam("Authorization") final String authorization) {
		
		if (this.connectAndLogin(authorization)) {

			final FTPFile[] res = FTPRestService.ftpService
					.listDirectory(dirName);
			String listing = "";
			for (int i = 0; i < res.length; i++) {
				listing += res[i].getRawListing() + "<br />";
			}

			return Response.ok().entity(listing).build();
		} else {
			return Response.status(Status.UNAUTHORIZED)
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
	@Path("/file/{name: .+}")
	public Response getFile(@PathParam(value = "name") final String fileName,
			@HeaderParam("Authorization") final String authorization) {

		if (this.connectAndLogin(authorization)) {

			return FTPRestService.ftpService.getFile(fileName);
		} else {
			return Response.status(Status.UNAUTHORIZED)
					.entity("Impossible to connect or log in").build();
		}

	}

	/**
	 *
	 * @param name
	 * @param authorization
	 * @return
	 */
	private Response delete(final String name, final String authorization) {
		Response response = null;
		if (this.connectAndLogin(authorization)) {
			if (FTPRestService.ftpService.delete(name)) {
				System.out.println("Deletion successfull");
				response = Response.ok().build();
			} else {
				response = Response
						.status(Status.FORBIDDEN)
						.entity("Impossible to delete the given directory/file")
						.build();
			}
		} else {
			response = Response.status(Status.FORBIDDEN)
					.entity("Impossible to delete the given directory/file")
					.build();
		}
		return response;
	}

	/**
	 * Delete the given directory.
	 *
	 * @param dirName
	 *            the directory
	 * @param authorization
	 *            the content of the HTTP header authorization
	 * @return True if the deletion is successful. False, if the directory
	 *         contains some files or subdirectories, or it does not exists.
	 */
	@DELETE
	@Path("/folder/{folder: .+}")
	public Response deleteDirectory(
			@PathParam(value = "folder") final String dirName,
			@HeaderParam("Authorization") final String authorization) {
		return this.delete(dirName, authorization);
	}

	/**
	 * Delete the given file.
	 *
	 * @param filename
	 *            the file
	 * @param authorization
	 *            the content of the HTTP header authorization
	 * @return True if the deletion is successful. False, if the does not
	 *         exists.
	 */
	@DELETE
	@Path("/file/{file: .+}")
	public Response deleteFile(
			@PathParam(value = "file") final String filename,
			@HeaderParam("Authorization") final String authorization) {
		return delete(filename, authorization);
	}

	@PUT
	@Path("/file/{file: .+}")
	public Response putFile(@PathParam(value = "file") final String remote,
			final InputStream fileInStream,
			@HeaderParam("Authorization") final String authorization){
		
		if (this.connectAndLogin(authorization)) {

			try {
				return FTPRestService.ftpService.putFile(remote, fileInStream);
			} catch (IOException e) {
				return Response.status(Status.UNAUTHORIZED)
						.entity("Directory inaccessible").build();
			}
		} else {
			return Response.status(Status.UNAUTHORIZED)
					.entity("Impossible to connect or log in").build();
		}
		
	}
}
