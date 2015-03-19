package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * This class test every ressources reachable with POST HTTP request
 *
 * @author arctarus
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class POSTTest extends BaseTesting {

	private final String DIR_NAME = "dirname";

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Try to create a directory.
	 */
	@Test
	public void testCreateDirectoryOK() {
		HttpResponse response = null;
		try {
			response = this.createDirectory(this.DIR_NAME, this.CORRECT_LOGIN,
					this.CORRECT_PASSWORD);
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		System.out.println(response.getStatusLine().getStatusCode());
		assertTrue(response.getStatusLine().getStatusCode() == 200);
	}

	/**
	 * Try to create an directory when another with the same name exists.
	 */
	@Test
	public void testCreateDirectorySameTwice() {
		HttpResponse response = null;
		try {
			response = this.createDirectory(this.DIR_NAME, this.CORRECT_LOGIN,
					this.CORRECT_PASSWORD);
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		assertTrue(response.getStatusLine().getStatusCode() == 403);
	}

	/**
	 * Try to create a directory without proper couple user/password.
	 */
	@Test
	public void testCreateDirectoryUnauthorized() {
		HttpResponse response = null;
		try {
			response = this.createFile(this.DIR_NAME, this.INCORRECT_LOGIN,
					this.INCORRECT_PASSWORD);
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		// assertTrue(response.getStatusLine().getStatusCode() == 401);
	}

	/**
	 * Try to create a file.
	 */
	@Test
	public void testCreateFileOK() {
		HttpResponse response = null;
		try {
			response = this.createFile("fichier", this.CORRECT_LOGIN,
					this.CORRECT_PASSWORD);
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		assertTrue(response.getStatusLine().getStatusCode() == 200);
	}

	/**
	 *
	 */
	@Test
	public void testCreateFileUnauthorized() {
		HttpResponse response = null;
		try {
			response = this.createFile("fichier", this.INCORRECT_LOGIN,
					this.INCORRECT_PASSWORD);
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		System.out.println(response.getStatusLine().getStatusCode());
		assertTrue(response.getStatusLine().getStatusCode() == 401);
	}
}
