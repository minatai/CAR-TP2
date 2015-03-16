package restFTP.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPFileFilters;
import org.apache.commons.net.ftp.FTPReply;

/**
 * This class provide an easy way to handle communications with to FTP Server.
 *
 * @author Arthur Dewarumez
 *
 */
// TODO ajout d'une structure pour stocker un grand nombre de session (HashMap
// (id/session))
public class FTPService {

	private static final String PARENT_LINK = "<a href=\"http://localhost:8080/rest/api/dir/p\"/>..</a></br>";
	private static final String HERE = "here";
	private static final String STOR_FORM = "</br><a href=\"http://localhost:8080/rest/api/formStor\"> Add </a>";
	private static final String FILE_PATH_LINK = "<a href=\"http://localhost:8080/rest/api/file/";
	private static final String DIR_PATH_LINK = "<a href=\"http://localhost:8080/rest/api/dir/";
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

	// /**
	// * Create a new FTPConnector to the specify address and port
	// *
	// * @param address
	// * the address of the FTP server
	// * @param port
	// * the port on which the server listen.
	// */
	// public FTPService(final String hostName, final int port) {
	// this.hostName = hostName;
	// this.port = port;
	// this.ftpClient = new FTPClient();
	// this.login = null;
	// }

	private FTPService() {
		this.ftpClient = new FTPClient();
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
	 * @return a list of filename for every files in the directory
	 * @throws FTPConnectionClosedException
	 */
	public String listDirectory(final String dir) {
		
		try {
			System.out.println(this.ftpClient.printWorkingDirectory()+ "/"
					+ dir);
			String directory = this.ftpClient.printWorkingDirectory()+ "/"
					+ dir;
			this.ftpClient.changeWorkingDirectory(directory);
			System.out. println("appel");
			System.out.println("on commence");
			FTPFile[] filenames;
			filenames = this.ftpClient.listFiles(dir);
			System.out.println("affichage de fichier");
			System.out.println(filenames.length);
			for (int i = 0; i < filenames.length; i++) {
				System.out.println(i);

				System.out.println(filenames[i].getName());
			}
<<<<<<< HEAD
			System.out.println("fin");
		} catch (IOException e) {
=======
		} catch (final IOException e) {
			System.out
					.println("Erreur: Impossible d'afficher la liste des fichiers");
>>>>>>> 2ea220bb40d8cb4a01dc4b02398a3e63fddedb4e
			e.printStackTrace();
		}
		
		return "";
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
