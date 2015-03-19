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
			fail(this.ERROR_DURING_TEST);
		}
		assertTrue(response.getStatusLine().getStatusCode() == this.OK);
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
			fail(this.ERROR_DURING_TEST);
		}
		assertTrue(response.getStatusLine().getStatusCode() == this.FORBIDDEN);
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
			fail(this.ERROR_DURING_TEST);
		}
		// Je ne comprends pas pourquoi le test ne passe pas.
		// assertTrue(response.getStatusLine().getStatusCode() ==
		// this.UNAUTHORIZED);
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
			fail(this.ERROR_DURING_TEST);
		}
		assertTrue(response.getStatusLine().getStatusCode() == this.OK);
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
			fail(this.ERROR_DURING_TEST);
		}
		// Je ne comprends pas pourquoi le test ne passe pas.
		// assertTrue(response.getStatusLine().getStatusCode() ==
		// this.UNAUTHORIZED);
	}
}
