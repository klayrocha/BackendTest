package com.klayrocha.revolut;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Class responsible for starting an HTTP server
 * @author klayrocha
 *
 */
public class Main {

	public static final String BASE_URI = "http://localhost:8085/revolut/";

	/**
	 * Method responsible for starting an HTTP server
	 * @return HttpServer
	 */
	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig().packages("com.klayrocha.revolut");
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		System.out.println(String.format("Running ....%s\npress enter to stop...", BASE_URI));
		System.in.read();
		server.shutdown();
	}
}
