package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.filter.Filter;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.BlockedResponse;
import com.iprogrammerr.bright.server.response.IntermediateResponse;
import com.iprogrammerr.bright.server.response.ToForwardResponse;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;

public final class AuthorizationFilter implements Filter {

	private static final String SECRET_TOKEN = "token";
	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public IntermediateResponse response(Request request) {
		IntermediateResponse response;
		if (request.hasHeader(AUTHORIZATION_HEADER)) {
			String token = request.header(AUTHORIZATION_HEADER);
			boolean valid = token.equals(SECRET_TOKEN);
			response = valid ? new ToForwardResponse() : new BlockedResponse(new ForbiddenResponse());
		} else {
			response = new BlockedResponse(new ForbiddenResponse());
		}
		return response;
	}
}
