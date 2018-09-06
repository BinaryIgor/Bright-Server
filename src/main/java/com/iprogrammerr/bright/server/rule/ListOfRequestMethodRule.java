package com.iprogrammerr.bright.server.rule;

import com.iprogrammerr.bright.server.request.RequestMethod;

public class ListOfRequestMethodRule implements RequestMethodRule {

    private RequestMethod[] requestMethods;

    public ListOfRequestMethodRule(RequestMethod... requestMethods) {
	this.requestMethods = requestMethods;
    }

    @Override
    public boolean isCompliant(String requestMethod) {
	for (RequestMethod method : requestMethods) {
	    if (method.equalsByValue(requestMethod)) {
		return true;
	    }
	}
	return false;
    }

}
