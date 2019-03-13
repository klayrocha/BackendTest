package com.klayrocha.revolut.rest;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.klayrocha.revolut.entity.Account;
import com.klayrocha.revolut.service.AccountService;

/**
 * Service class restful for Account
 * @author klayrocha
 *
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AccountResource {

	/**
	 * Method responsible for searching the account by the informed
	 * @param iban
	 * @return account
	 */
	@Path("{iban}")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response retrieveAccount(@PathParam("iban") String iban) {
		AccountService service = new AccountService();
		Account account = service.findByIban(iban);
		return Response.status(Status.OK).entity(account).build();
	}
	
	/**
	 * Method responsible for including an account
	 * @param account
	 * @return account
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response insertAccount(Account account) {
		AccountService service = new AccountService();
		service.insertAccount(account);
		return Response.status(Status.CREATED).entity(account).build(); 
	}

}
