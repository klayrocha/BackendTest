package com.klayrocha.revolut.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.klayrocha.revolut.entity.ErrorMessage;

/**
 * Exception Class for Account Not Found That Responds to ErrorMessage Class
 * @author klayrocha
 *
 */
@Provider
public class AccountNotFoundExceptionMapper implements ExceptionMapper<AccountNotFoundException>{

	@Override
	public Response toResponse(AccountNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(),404);
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}

}
