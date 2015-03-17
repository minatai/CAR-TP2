package restFTP.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * This class provide an easy way to handle communications with to FTP Server.
 *
 * @author Arthur Dewarumez and Imane KHEMICI
 *
 */
// TODO ajout d'une structure pour stocker un grand nombre de session (HashMap
// (id/session))
public class FTPService {

	private final String hostName = "localhost";
	private final int port = 9999;
	private final FTPClient ftpClient;
	private String login;
	private static FTPService instance = null;

	public static synchronized FTPService getInstance() {
		if (FTPService.instance == null) {
			FTPService.instance = new FTPService();
		}
		return FTPService.instance;
	}

	/**
	 * if entry is a directory then return true else return false
	 *
	 * @param entry
	 * @return
	 */
	public boolean isADirectory(final String entry) {
		return new File(entry).isDirectory();
	}

	private FTPService() {
		this.ftpClient = new FTPClient();
		final FTPClientConfig ftpConf = new FTPClientConfig(
				FTPClientConfig.SYST_UNIX);
		this.ftpClient.configure(ftpConf);
		this.login = null;
	}

	/**
	 * Return if the passerelle is connected to the FTP server.
	 *
	 * @return
	 */
	public boolean isConnected() {
		return this.ftpClient.isConnected();
	}

	/**
	 * Connect the FTPConnector to the server specified during the
	 * instantiation.
	 */
	public boolean connect() {
		System.out.println("[Server] New attempt to login.");
		try {
			this.ftpClient.connect(this.hostName, this.port);
			final int reply = this.ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				this.ftpClient.disconnect();
				return false;
			}
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		}
		this.ftpClient.enterLocalActiveMode();
		System.out.println("[Server] Successfull login.");
		return true;
	}

	/**
	 * Login to the FTP server with the username and password given.
	 *
	 * @param userName
	 *            the username to use
	 * @param password
	 *            the password to use
	 * @return True if the login is successful. False otherwise
	 */
	public boolean login(final String userName, final String password) {
		this.login = userName;
		boolean result = false;
		try {
			result = this.ftpClient.login(userName, password);
		} catch (final IOException e) {
			System.out.printf(
					"[%s] I/O error occured while attempting a new login.\n",
					this.login);
			e.printStackTrace();
			return false;
		}
		if (result) {
			System.out.printf("[%s] Successfull login.\n", this.login);
		} else {
			System.out.printf("[%s] Bad password or login.\n", this.login);
		}
		return result;
	}

	/**
	 * Change the working directory to one given.
	 *
	 * @param pathname
	 *            the new working directory
	 * @return true if successfully completed. false otherwise.
	 */
	public boolean changeDirectory(final String pathname) {
		final boolean result = false;
		try {
			this.ftpClient.changeWorkingDirectory(pathname);
		} catch (final IOException e) {
			System.out
					.printf("[%s] I/O error occured while setting a new working directory.\n",
							this.login);
			e.printStackTrace();
		}
		if (result) {
			System.out.printf("[%s] New working directory %s\n.", this.login,
					pathname);
			return true;
		}
		System.out.printf(
				"[%s] Error while setting the new working directory.\n",
				this.login);
		return false;
	}

	/**
	 * Create a new directory in the current working directory.
	 *
	 * @param directory
	 *            the name of the new directory
	 * @return true if successfully completed, false otherwise.
	 */
	public boolean createDirectory(final String directory) {
		boolean result = false;
		try {
			result = this.ftpClient.makeDirectory(directory);
			if (result) {
				System.out.printf("[%s] New directory created.\n", this.login);
			}
		} catch (final IOException e) {
			System.out.printf("[%s] Error while creating the new directory.\n",
					this.login);
			e.printStackTrace();
			return false;
		}

		return result;
	}

	/**
	 * Create a new file.
	 *
	 * @param remote
	 *            the name of the new file. If this file name contains a
	 *            directory name, create the new file in.
	 * @param local
	 *            the InputStream of the new file
	 * @return return true if the file is created. False otherwise. The file is
	 *         not created, if the directory name does not exist.
	 */
	public boolean createFile(final String remote, final InputStream local) {
		try {
			if (this.ftpClient.storeFile(remote, local)) {
				System.out.printf("[%s] New file %s created.\n", this.login,
						remote);
				return true;
			}
		} catch (final IOException e) {
			System.out.printf("[%s] Error while creating a new file.\n",
					this.login);
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * List the files of the given directory
	 *
	 * @param directory
	 *            the directory to use
	 * @return an array of FTPFile. An empty if they are no file or directory.
	 *         null, if an error occurred.
	 * @throws FTPConnectionClosedException
	 */

	public FTPFile[] listDirectory(final String dir) {
		try {
			final FTPFile[] res = this.ftpClient.listFiles(dir);
			return res;
		} catch (final IOException e) {
			System.out
			.println("Erreur: Impossible d'afficher la liste des fichiers");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the containent of file
	 *
	 * @param
	 * @return
	 */
	public Response getFile(final String filename) {
		Response response = null;
		if (isADirectory(filename)) {
			response = Response.status(Response.Status.FORBIDDEN)
					.entity("Le fichier est un répertoire").build();
		} else {
			try {
				InputStream is;
				is = this.ftpClient.retrieveFileStream(filename);
				if (is == null) {
					response = Response.status(Response.Status.NOT_FOUND)
							.build();
				} else {
					response = Response.ok(is,
							MediaType.APPLICATION_OCTET_STREAM).build();
				}

			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	
	public Response putFile(String remote,InputStream file ) throws IOException{
		boolean existe = false;
		String[] dossiers = remote.split("/");
		int n = dossiers.length;
		String dir=""; 
		for(int i=0;i<n-1;i++){
			dir=dir+dossiers[i]+"/";
		}
		FTPFile[] files = listDirectory(dir);
		
		for(int i=0; i<files.length;i++){
			if(dossiers[n-1].equals(files[i].getName())){		
					existe = true;
			}
			
		}
		this.ftpClient.changeWorkingDirectory(dir);
		System.out.println(this.ftpClient.printWorkingDirectory());
		if(existe){
			createFile(dossiers[n-1], file);
			return Response.ok().build();
		}
		else{
			return Response.status(Status.FORBIDDEN)
					.entity("The file is not exist").build();
		}
}

	/**
	 * Delete the given file or directory.
	 *
	 * @param filename
	 *            the file or directory name
	 * @return true if the deletion is successfull. False, otherwise
	 */
	public boolean delete(final String filename) {
		try {
			return this.ftpClient.deleteFile(filename);
		} catch (final IOException e) {
			System.out.printf(
					"Impossible de supprimer le fichier/répertoire %s.\n",
					filename);
			e.printStackTrace();
			return false;
		}
	}
}
