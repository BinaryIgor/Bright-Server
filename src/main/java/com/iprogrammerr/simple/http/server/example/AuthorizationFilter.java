package com.iprogrammerr.simple.http.server.example;

import com.iprogrammerr.simple.http.server.constants.RequestHeaderKey;
import com.iprogrammerr.simple.http.server.constants.RequestMethod;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.filter.RequestFilter;
import com.iprogrammerr.simple.http.server.filter.RequestMethodRule;
import com.iprogrammerr.simple.http.server.filter.RequestUrlRule;
import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.model.Response;

public class AuthorizationFilter extends RequestFilter {

    public AuthorizationFilter() {
	super(RequestUrlRule.createAll(), RequestMethodRule.create(RequestMethod.GET, RequestMethod.POST));
    }

    @Override
    public boolean isValid(Request request, Response response) {
	if (!request.hasHeader(RequestHeaderKey.AUTHORIZATION)) {
	    response.setCode(ResponseCode.UNAUTHORIZED);
	    return false;
	}
	String token = request.getHeader(RequestHeaderKey.AUTHORIZATION);
	System.out.println("Token = " + token);
	boolean valid = token.equals("token");
	if (!valid) {
	    response.setCode(ResponseCode.UNAUTHORIZED);
	}
	return valid;
    }

}
