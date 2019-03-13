package com.klayrocha.revolut.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.klayrocha.revolut.entity.ErrorMessage;

/**
 * Exception class when adding the Account That Responds to ErrorMessage Class
 * @author klayrocha
 *
 */
@Provider
public class InsertAccountExceptionMapper implements ExceptionMapper<InsertAccountException> {

	@Override
	public Response toResponse(InsertAccountException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 400);
		return Response
				.status(Status.BAD_REQUEST)
				.entity(errorMessage).build();
	}
}
