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
	    return new ForbiddenResponse();
	}
	try {
	    String token = request.header(AUTHORIZATION_HEADER);
	    boolean valid = token.equals(SECRET_TOKEN);
	    if (!valid) {
		return new ForbiddenResponse();
	    }
	    return new OkResponse();
	} catch (Exception exception) {
	    return new ForbiddenResponse();
	}
    }

}
