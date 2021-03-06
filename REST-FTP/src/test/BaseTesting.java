package test;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpDelete;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public abstract class BaseTesting {

	protected final String host = "http://localhost";
	protected final int port = 8080;
	protected final String APIBaseURL = "/rest/api/ftp/";
	protected final String completeURL = this.host + ":" + this.port
			+ this.APIBaseURL;
	protected final String folderRessource = "folder/";
	protected final String fileRessource = "file/";
	protected final String deleteRessource = "delete/";
	protected final String HEADER_AUTHORIZATION = "Authorization";
	private final String START_FIELD_AUTH = "Basic ";
	protected final String CORRECT_LOGIN = "arctarus";
	protected final String CORRECT_PASSWORD = "test";
	protected final String INCORRECT_LOGIN = "nope";
	protected final String INCORRECT_PASSWORD = "nope";
	protected final String ERROR_DURING_TEST = "An error occured during the test";
	protected final int OK = 200;
	protected final int UNAUTHORIZED = 401;
	protected final int FORBIDDEN = 403;

	protected final HttpClient client;

	/**
	 * Create the content of an authorization HTTP header with the login and
	 * password given.
	 *
	 * @param login
	 *            the login
	 * @param password
	 *            the password
	 * @return the content of the authorization HTTp header
	 */
	private String createAuthorizationField(final String login,
			final String password) {
		final String contentFieldStr = login + ":" + password;
		final byte[] contentField = Base64.encodeBase64(contentFieldStr
				.getBytes());
		return this.START_FIELD_AUTH + new String(contentField);

	}

	public BaseTesting() {
		this.client = HttpClientBuilder.create().build();
	}

	public HttpResponse createDirectory(final String dirName,
			final String login, final String password)
			throws ClientProtocolException, IOException {
		final HttpPost request = new HttpPost(this.completeURL
				+ this.folderRessource + dirName);
		request.addHeader(this.HEADER_AUTHORIZATION,
				this.createAuthorizationField(login, password));
		return this.client.execute(request);
	}

	public HttpResponse createFile(final String filename, final String login,
			final String password) throws ClientProtocolException, IOException {
		final HttpPost request = new HttpPost(this.completeURL
				+ this.fileRessource + filename);
		request.addHeader(this.HEADER_AUTHORIZATION,
				this.createAuthorizationField(login, password));
		return this.client.execute(request);
	}
	
	public HttpResponse getFile(final String filename, String login, String password) throws ClientProtocolException, IOException{
		final HttpGet request = new HttpGet(this.completeURL+fileRessource+filename );
		request.addHeader(this.HEADER_AUTHORIZATION,
				this.createAuthorizationField(login, password));
		return this.client.execute(request);
	}
	
	public HttpResponse getDirectory(final String dirname, String login, String password) throws ClientProtocolException, IOException{
		final HttpGet request = new HttpGet(this.completeURL+folderRessource+dirname );
		request.addHeader(this.HEADER_AUTHORIZATION,
				this.createAuthorizationField(login, password));
		return this.client.execute(request);
	}

	public HttpResponse delete(final String name, final String login,
			final String password) {
		final HttpDelete request = new HttpDelete(this.completeURL
				+ this.deleteRessource + name);
		request.addHeader(this.HEADER_AUTHORIZATION,
				this.createAuthorizationField(login, password));
		try {
			return this.client.execute(request);
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
