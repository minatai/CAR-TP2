package restFTP.restService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import restFTP.main.Starter;
import restFTP.service.FTPService;

@Path("/ftp")
public class FTPRestService {

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

	@POST
	@Path("/folder/{name}")
	public String createDirectory(
			@PathParam(value = "name") final String dirName) {
		final boolean result = false;
		if (this.connectAndLogin()) {
			if (FTPRestService.ftpService.createDirectory(dirName)) {
				return "Directory " + dirName + " created.";
			}
			return "Can not create directory";
		} else {
			return "Impossible to connect or log in";
		}
	}
}
