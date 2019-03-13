package com.klayrocha.revolut.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.klayrocha.revolut.Main;
import com.klayrocha.revolut.entity.Account;
import com.klayrocha.revolut.helper.AccountBuilder;

/**
 * Test class for AccountResource class
 * 
 * @author klayrocha
 *
 */
public class AccountResourceTest {

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
	public void testRecoverAccount() {
		String responseMsg = target.path("account/PT233920012223230888").request().get(String.class);
		assertTrue(responseMsg.contains("PT233920012223230888"));
	}

	@Test
	public void testInsertAccount() {
		Account account = AccountBuilder.oneAccount().now();

		Response response = target.path("account")
				.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8")
				.post(Entity.entity(account, "application/json"));

		assertEquals(201, response.getStatus());
	}

	@Test
	public void testInsertAccountIbanNull() {
		Account account = AccountBuilder.oneAccount().ibanNull().now();
		Response response = target.path("account")
				.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8")
				.post(Entity.entity(account, "application/json"));

		assertEquals(400, response.getStatus());
	}

	@Test
	public void testInsertAccountNumberNull() {
		Account account = AccountBuilder.oneAccount().numberNull().now();

		Response response = target.path("account")
				.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8")
				.post(Entity.entity(account, "application/json"));

		assertEquals(400, response.getStatus());
	}

	@Test
	public void testInsertAccountIbanInvalid() {
		Account account = AccountBuilder.oneAccount().ibanInvalid().now();

		Response response = target.path("account")
				.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8")
				.post(Entity.entity(account, "application/json"));

		assertEquals(400, response.getStatus());
	}

}
