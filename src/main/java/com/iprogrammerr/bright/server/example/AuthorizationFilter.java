package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.filter.RequestFilter;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class AuthorizationFilter implements RequestFilter {

    private static final String SECRET_TOKEN = "token";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public Response filter(Request request) {
	if (!request.hasHeader(AUTHORIZATION_HEADER)) {
	    System.out.println("There is no authorization header!");
	    return new ForbiddenResponse();
	}
	String token = request.header(AUTHORIZATION_HEADER);
	System.out.println("Secret token = " + token);
	boolean valid = token.equals(SECRET_TOKEN);
	if (!valid) {
	    return new ForbiddenResponse();
	}
	System.out.println("Secret header of " + token + " is valid");
	return new OkResponse();
    }

}
