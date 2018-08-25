package com.iprogrammerr.simpleserver.example;

import java.util.List;

import com.iprogrammerr.simpleserver.constants.RequestHeaderKey;
import com.iprogrammerr.simpleserver.constants.RequestMethod;
import com.iprogrammerr.simpleserver.constants.ResponseCode;
import com.iprogrammerr.simpleserver.filter.RequestFilter;
import com.iprogrammerr.simpleserver.filter.RequestMethodRule;
import com.iprogrammerr.simpleserver.filter.RequestUrlRule;
import com.iprogrammerr.simpleserver.model.Parameter;
import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

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
	List<Parameter> parameters = request.getParameters();
	for (Parameter parameter : parameters) {
	    System.out.println(parameter);
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
