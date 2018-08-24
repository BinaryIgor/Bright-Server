package com.iprogrammerr.simpleserver.example;

import com.iprogrammerr.simpleserver.constants.RequestMethod;
import com.iprogrammerr.simpleserver.constants.ResponseCode;
import com.iprogrammerr.simpleserver.filter.RequestFilter;
import com.iprogrammerr.simpleserver.filter.RequestMethodRule;
import com.iprogrammerr.simpleserver.filter.RequestUrlRule;
import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

public class AuthorisationFilter extends RequestFilter {

    public AuthorisationFilter() {
	super(RequestUrlRule.createStartsWith("simple/user"),
		RequestMethodRule.create(RequestMethod.GET, RequestMethod.POST));
    }

    @Override
    public boolean isValid(Request request, Response response) {
	System.out.println("Not good!");
	response.setCode(ResponseCode.CONFLICT);
	return false;
    }

}
