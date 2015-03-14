package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;

public class POSTTest extends BaseTesting {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateDirectoryOK() {
		HttpResponse response = null;
		try {
			response = this.createDirectory("Maman");
		} catch (final ClientProtocolException e) {
			fail("Le serveur est surement pas lancé.");
			e.printStackTrace();
		} catch (final IOException e) {
			fail("Une erreur est intervenu durant la communication")
			e.printStackTrace();
		}
		assertTrue(response.getStatusLine().getStatusCode() == 200);
	}

	public void testCreateDirectSameTwice() {

	}
}
