package com.iprogrammerr.simple.http.server.example;

import com.iprogrammerr.simple.http.server.constants.RequestHeaderKey;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.filter.RequestValidator;
import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.model.Response;

public class AuthorizationValidator implements RequestValidator {

    private static final String SECRET_TOKEN = "token";

    @Override
    public boolean isValid(Request request, Response response) {
	if (!request.hasHeader(RequestHeaderKey.AUTHORIZATION)) {
	    response.setCode(ResponseCode.UNAUTHORIZED);
	    return false;
	}
	String token = request.getHeader(RequestHeaderKey.AUTHORIZATION);
	boolean valid = token.equals(SECRET_TOKEN);
	if (!valid) {
	    response.setCode(ResponseCode.UNAUTHORIZED);
	}
	return valid;
    }

}
