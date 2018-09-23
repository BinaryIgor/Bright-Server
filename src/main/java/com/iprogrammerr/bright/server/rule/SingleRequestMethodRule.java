package com.iprogrammerr.bright.server.rule;

import com.iprogrammerr.bright.server.method.RequestMethod;

public final class SingleRequestMethodRule implements RequestMethodRule {

    private final RequestMethod requestMethod;

    public SingleRequestMethodRule(RequestMethod requestMethod) {
	this.requestMethod = requestMethod;
    }

    @Override
    public boolean compliant(String requestMethod) {
	return this.requestMethod.is(requestMethod);
    }

}
