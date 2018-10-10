package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.filter.Filter;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class AuthorizationFilter implements Filter {

    private static final String SECRET_TOKEN = "token";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public Response response(Request request) {
	Response response;
	if (request.hasHeader(AUTHORIZATION_HEADER)) {
	    String token = request.header(AUTHORIZATION_HEADER);
	    boolean valid = token.equals(SECRET_TOKEN);
	    response = valid ? new OkResponse() : new ForbiddenResponse();
	} else {
	    response = new ForbiddenResponse();
	}
	return response;
    }

}
