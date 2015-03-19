package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import restFTP.restService.FTPRestService;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class GetTest extends BaseTesting{
	
	@Test
	public void testGetFileOK() {
		HttpResponse response = null;
		try {
			response = this.getFile("fichier", this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		System.out.println(response.getStatusLine().getStatusCode());
		assertTrue(response.getStatusLine().getStatusCode() == 200);
	
	}

	@Test
	public void testGetFileKO() {
		HttpResponse response = null;
		try {
			response = this.getFile("file", this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		System.out.println(response.getStatusLine().getStatusCode());
		assertTrue(response.getStatusLine().getStatusCode() == 404);
	
	}
	
	@Test
	public void testGetDirectoryOK() {
		HttpResponse response = null;
		try {
			response = this.getDirectory("CV/", this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		System.out.println(response.getStatusLine().getStatusCode());
		assertTrue(response.getStatusLine().getStatusCode() == 200);

	}
}
