package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;

public class DELETETest extends BaseTesting {

	private final String DIR_EMPTY = "dir_vide";
	private final String DIR_FULL = "dir_plein";
	private final String FICHIER = "fichier";
	private final String FICHIER1 = "fichier1";
	private final String FICHIER2 = "fichier2";

	/**
	 * Create the environment of test.
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.createDirectory(this.DIR_EMPTY, this.CORRECT_LOGIN,
				this.CORRECT_PASSWORD);
		this.createDirectory(this.DIR_FULL, this.CORRECT_LOGIN,
				this.CORRECT_PASSWORD);
		this.createFile(this.FICHIER, this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		this.createFile(this.DIR_FULL + "/" + this.FICHIER1,
				this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		this.createFile(this.DIR_FULL + "/" + this.FICHIER2,
				this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
	}

	@Test
	public void deleteEmptyDirectory() {
		final HttpResponse response = this.delete(this.DIR_EMPTY,
				this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		if (response == null) {
			fail(this.ERROR_DURING_TEST);
		} else {
			assertTrue(response.getStatusLine().getStatusCode() == this.OK);
		}
	}

	@Test
	public void deleteDirectoryWithFiles() {
		final HttpResponse response = this.delete(this.DIR_FULL,
				this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		if (response == null) {
			fail(this.ERROR_DURING_TEST);
		} else {
			System.out.println("delete dir with files "
					+ response.getStatusLine().getStatusCode() + " expected "
					+ this.FORBIDDEN);
			assertTrue(response.getStatusLine().getStatusCode() == this.FORBIDDEN);
		}
	}

	@Test
	public void deleteExistingFile() {
		final HttpResponse response = this.delete(this.DIR_EMPTY,
				this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		if (response == null) {
			System.out.println("loiol");
		} else {
			assertTrue(response.getStatusLine().getStatusCode() == this.OK);
		}
	}

	@Test
	public void deleteExistingDirectory() {
		final HttpResponse response = this.delete(this.DIR_EMPTY,
				this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		if (response == null) {
			fail(this.ERROR_DURING_TEST);
		} else {
			assertTrue(response.getStatusLine().getStatusCode() == this.OK);
		}
	}

	@Test
	public void deleteNonexistingFile() {
		final HttpResponse response = this.delete(this.DIR_EMPTY,
				this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		if (response == null) {
			fail(this.ERROR_DURING_TEST);
		} else {
			System.out.println("delete not exist file "
					+ response.getStatusLine().getStatusCode() + " expected "
					+ this.FORBIDDEN);
			assertTrue(response.getStatusLine().getStatusCode() == this.FORBIDDEN);
		}
	}

	@Test
	public void deleteNonexistingDirectory() {
		final HttpResponse response = this.delete(this.DIR_EMPTY,
				this.CORRECT_LOGIN, this.CORRECT_PASSWORD);
		if (response == null) {
			fail(this.ERROR_DURING_TEST);
		} else {
			assertTrue(response.getStatusLine().getStatusCode() == this.FORBIDDEN);
		}
	}

	@Test
	public void deleteBadLogin() {
		final HttpResponse response = this.delete(this.DIR_EMPTY,
				this.INCORRECT_LOGIN, this.INCORRECT_PASSWORD);
		if (response == null) {
			fail(this.ERROR_DURING_TEST);
		} else {
			System.out.println("bad login "
					+ response.getStatusLine().getStatusCode() + " expected "
					+ this.UNAUTHORIZED);
			assertTrue(response.getStatusLine().getStatusCode() == this.UNAUTHORIZED);
		}
	}
}
