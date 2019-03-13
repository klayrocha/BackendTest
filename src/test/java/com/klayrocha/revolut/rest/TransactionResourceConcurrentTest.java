package com.klayrocha.revolut.rest;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.klayrocha.revolut.Main;
import com.klayrocha.revolut.entity.TransactionDTO;
import com.klayrocha.revolut.helper.TransactionDTOBuilder;

/**
 * Test class for TransactionResource class for concurrent Tests
 * @author klayrocha
 *
 */
@RunWith(ConcurrentTestRunner.class)
public class TransactionResourceConcurrentTest {

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
	@ThreadCount(100)
	public void testConcurrentTransfer() {
		TransactionDTO dto = TransactionDTOBuilder.oneTransactionDTO().now();
		
		target.path("transaction")
				.request(MediaType.APPLICATION_JSON_TYPE+";charset=utf-8")
				.post(Entity.entity(dto, "application/json"));
		String responseMsg = target.path("account/PT233920012223230888").request().get(String.class);
		BigDecimal balance = new BigDecimal(responseMsg.substring(responseMsg.indexOf("balance")+9,responseMsg.indexOf("transactions")-2));
		assertTrue(balance.intValue() >= 0);
	}
	
}

