package com.klayrocha.revolut.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.klayrocha.revolut.entity.TransactionDTO;
import com.klayrocha.revolut.service.AccountService;

/**
 * Service class restful for Transaction
 * @author klayrocha
 *
 */
@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class TransactionResource {

	/**
	 * Method responsible for transferring money
	 * @param transactionDTO
	 * @return status
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response transfer(TransactionDTO transactionDTO) {
		AccountService service = new AccountService();
		service.transfer(transactionDTO);
		return Response.status(Status.OK).build(); 
	}
}
