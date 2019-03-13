package com.klayrocha.revolut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Main class
 * @author klayrocha
 *
 */
public class MainTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void startServer() throws Exception {
		server = Main.startServer();
		Client c = ClientBuilder.newClient();
		target = c.target(Main.BASE_URI);
	}

	@After
	public void shutdown() throws Exception {
		server.shutdown();
	}

	@Test
	public void testServerConnection() {
		String responseMsg = target.path("account/PT233920012223230888").request().get(String.class);
		assertNotNull(responseMsg);
	}
	
	@Test
	public void testUriInvalid() {
		Response response  = target.path("accountInvalid/PT233920012223230888").request().get();
		assertEquals(500,response.getStatus());
	}
}
