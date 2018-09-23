package com.iprogrammerr.bright.server.rule;

import com.iprogrammerr.bright.server.method.RequestMethod;

public final class ListOfRequestMethodRule implements RequestMethodRule {

    private final RequestMethod[] requestMethods;

    public ListOfRequestMethodRule(RequestMethod... requestMethods) {
	this.requestMethods = requestMethods;
    }

    @Override
    public boolean compliant(String requestMethod) {
	for (RequestMethod method : requestMethods) {
	    if (method.is(requestMethod)) {
		return true;
	    }
	}
	return false;
    }

}
