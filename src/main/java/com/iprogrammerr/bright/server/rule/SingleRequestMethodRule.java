package com.iprogrammerr.bright.server.rule;

public class SingleRequestMethodRule implements RequestMethodRule {

    private String requestMethod;

    public SingleRequestMethodRule(String requestMethod) {
	this.requestMethod = requestMethod;
    }

    @Override
    public boolean isCompliant(String requestMethod) {
	return this.requestMethod.equalsIgnoreCase(requestMethod);
    }

}
