package com.iprogrammerr.simple.http.server.rule;

public class ListOfRequestMethodRule implements RequestMethodRule {

    private String[] requestMethods;

    public ListOfRequestMethodRule(String... requestMethods) {
	this.requestMethods = requestMethods;
    }

    @Override
    public boolean isCompliant(String requestMethod) {
	for (String method : requestMethods) {
	    if (method.equalsIgnoreCase(requestMethod)) {
		return true;
	    }
	}
	return false;
    }

}
