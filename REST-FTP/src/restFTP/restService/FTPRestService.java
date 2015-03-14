package restFTP.restService;

import java.io.InputStream;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import restFTP.main.Starter;
import restFTP.service.FTPService;

@Path("/ftp")
public class FTPRestService {

	/**
	 * The tools used to connecte to the FTP server.
	 */
	private static FTPService ftpService = FTPService.getInstance();

	/**
	 * If necessary, connect to the FTP server and log in with the credentiels
	 * set in the class Started
	 *
	 * @return true if the sequence successfully completed. False otherwise.
	 */
	private boolean connectAndLogin() {
		if (!FTPRestService.ftpService.isConnected()) {
			if (FTPRestService.ftpService.connect()) {
				return FTPRestService.ftpService.login(Starter.userName,
						Starter.password);
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
	 * @return a string to send to the client who represent the result of this
	 *         operation
	 */
	@POST
	@Path("/folder/{name}")
	public String createDirectory(
			@PathParam(value = "name") final String dirName) {
		if (this.connectAndLogin()) {
			if (FTPRestService.ftpService.createDirectory(dirName)) {
				return "Directory " + dirName + " created.";
			}
			return "Can not create directory";
		} else {
			return "Impossible to connect or log in";
		}
	}

	/**
	 * Create a new file.
	 *
	 * @param remote
	 *            the name of the new file. If this file name contains a
	 *            directory name, create the new file in.
	 * @param fileInStream
	 *            the InputStream of the new file
	 * @return a string to send to the client who represent the result of this
	 *         operation
	 */
	@POST
	@Path("/file/{name: .*}")
	public String createFile(@PathParam(value = "name") final String remote,
			final InputStream fileInStream) {
		System.out.println("*********************************************\n"
				+ remote + "*********************************************\n");
		if (this.connectAndLogin()) {
			if (FTPRestService.ftpService.createFile(remote, fileInStream)) {
				return "File created.";
			} else {
				return "The file is not created.";
			}
		} else {
			return "Impossible to connect or log in";
		}
	}

	/**
	 * Create a new session on the FTP server
	 *
	 * @param login
	 *            the login used for the authentication
	 * @param password
	 *            the password used for the authentication
	 * @return a response representing the state of the connection.
	 */
	@POST
	@Path("/login/{username}/{password}")
	// TODO retourner un cookie avec un ID de session
	// TODO adapter méthode en fonction correction apporté à FTPService.login
	public Response login(@PathParam(value = "username") final String username,
			@PathParam(value = "password") final String password) {
		if (!FTPRestService.ftpService.connect()) {
			return Response.status(500).build();
		}
		if (FTPRestService.ftpService.login(username, password)) {
			final NewCookie cookie = new NewCookie("Session", "123456");
			return Response.ok().cookie(cookie).build();
		}
		return Response.status(401).build();
	}

	/**
	 *
	 *
	 */
	@GET
	@Path("/folder/{name}")
	public String listDirectory(@PathParam(value = "name") final String dirName) {
		System.out.println("*********************************************\n"
				+ dirName + "*********************************************\n");
		if (this.connectAndLogin()) {
			// final List<String> listContenu = this.ftpService
			// .listDirectory(dirName);
			this.ftpService.listDirectory(dirName);
			// String liste = "";
			// System.out.println(liste);
			//
			// for (final String s : listContenu) {
			// liste = liste + s + "\n";
			// }
			// return liste;
			return "";
		} else {
			return "Impossible to connect or log in";
		}

	}

	/**
	 * Delete the given directory.
	 *
	 * @param dirName
	 *            the directory
	 * @return True if the deletion is successful. False, if the directory
	 *         contains some files or subdirectories, or it does not exists.
	 */
	@DELETE
	@Path("/folder/{folder: .+}")
	public String deleteDirectory(
			@PathParam(value = "folder") final String dirName) {
		if (this.connectAndLogin()) {
			if (FTPRestService.ftpService.delete(dirName)) {
				return "Deletion successfull";
			} else {
				return "Impossible to delete the given directory/file";
			}
		} else {
			return "Impossible to connect or log in";
		}
	}

	/**
	 * Delete the given file.
	 *
	 * @param filename
	 *            the file
	 * @return True if the deletion is successful. False, if the does not
	 *         exists.
	 */
	@DELETE
	@Path("/file/{file: .+}")
	public String deleteFile(@PathParam(value = "file") final String filename) {
		return deleteDirectory(filename);
	}
}
