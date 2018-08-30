package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.constants.RequestHeaderKey;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.resolver.RequestHandler;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.Response;

public class AuthorizationHandler implements RequestHandler {

    private static final String SECRET_TOKEN = "token";

    @Override
    public Response handle(Request request) {
	if (!request.hasHeader(RequestHeaderKey.AUTHORIZATION)) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	String token = request.getHeader(RequestHeaderKey.AUTHORIZATION);
	boolean valid = token.equals(SECRET_TOKEN);
	if (!valid) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	return new EmptyResponse(ResponseCode.OK);
    }

}
