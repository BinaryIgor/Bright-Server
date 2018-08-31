package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.constants.HeaderKey;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.filter.ToFilterRequestHandler;
import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.Response;

public class AuthorizationHandler implements ToFilterRequestHandler {

    private static final String SECRET_TOKEN = "token";

    @Override
    public Response handle(Request request) {
	if (!request.hasHeader(HeaderKey.AUTHORIZATION)) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	String token = request.getHeader(HeaderKey.AUTHORIZATION);
	boolean valid = token.equals(SECRET_TOKEN);
	if (!valid) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	System.out.println("Secret header of " + token + " is valid");
	return new EmptyResponse(ResponseCode.OK);
    }

}
