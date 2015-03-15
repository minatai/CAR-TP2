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
 * @author arctarus
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class POSTTest extends BaseTesting {

	private static int i = 0;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateDirectoryOK() {
		HttpResponse response = null;
		try {
			response = this.createDirectory("Test");
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		assertTrue(response.getStatusLine().getStatusCode() == 200);
	}

	@Test
	public void testCreateDirectorySameTwice() {
		HttpResponse response = null;
		try {
			response = this.createDirectory("Test");
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		assertTrue(response.getStatusLine().getStatusCode() == 403);
	}

	@Test
	public void testCreateFileOK() {
		HttpResponse response = null;
		try {
			response = this.createFile("fichier");
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		assertTrue(response.getStatusLine().getStatusCode() == 200);
	}

	@Test
	public void testCreateFileSameTwice() {
		HttpResponse response = null;
		try {
			response = this.createFile("fichier");
		} catch (final IOException e) {
			e.printStackTrace();
			fail("Une erreur est intervenu durant le test");
		}
		assertTrue(response.getStatusLine().getStatusCode() == 403);
	}
}
