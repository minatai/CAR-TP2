package test;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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

	protected final HttpClient client;

	public BaseTesting() {
		this.client = HttpClientBuilder.create().build();
	}

	public HttpResponse uploadFile(final String content) {
		return null;
	}

	public HttpResponse createDirectory(final String dirName)
			throws ClientProtocolException, IOException {
		final HttpPost request = new HttpPost(this.completeURL
				+ this.folderRessource + dirName);
		return this.client.execute(request);
	}

	public HttpResponse createFile(final String filename)
			throws ClientProtocolException, IOException {
		final HttpPost request = new HttpPost(this.completeURL
				+ this.fileRessource + filename);
		return this.client.execute(request);
	}

	public HttpResponse deleteDirectory(final String dirName) {
		return null;
	}

	public HttpResponse login(final String username, final String password) {
		return null;
	}
}
