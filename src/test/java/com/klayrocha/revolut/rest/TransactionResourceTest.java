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
import com.klayrocha.revolut.entity.TransactionDTO;
import com.klayrocha.revolut.helper.TransactionDTOBuilder;

/**
 * Test class for TransactionResource class
 * 
 * @author klayrocha
 *
 */
public class TransactionResourceTest {

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
	public void testTransfer() {
		TransactionDTO dto = TransactionDTOBuilder.oneTransactionDTO().now();

		Response response = target.path("transaction")
				.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8")
				.post(Entity
				.entity(dto, "application/json"));
		
		assertTrue(response.getStatus() == 200 || response.getStatus() == 400);
	}
	
	@Test
	public void testTransferValueZero() {
		TransactionDTO dto = TransactionDTOBuilder.oneTransactionDTO().valueZero().now();

		Response response = target.path("transaction")
				.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8")
				.post(Entity
				.entity(dto, "application/json"));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void testTransferInsufficientBalance() {
		TransactionDTO dto = TransactionDTOBuilder.oneTransactionDTO().highValue().now();

		Response response = target.path("transaction")
				.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8")
				.post(Entity
				.entity(dto, "application/json"));
		
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void testTransferIbanInvalid() {
		TransactionDTO dto = TransactionDTOBuilder.oneTransactionDTO().ibanDebitInvalid().now();

		Response response = target.path("transaction")
				.request(MediaType.APPLICATION_JSON_TYPE + ";charset=utf-8")
				.post(Entity
				.entity(dto, "application/json"));
		assertEquals(404, response.getStatus());
	}
}