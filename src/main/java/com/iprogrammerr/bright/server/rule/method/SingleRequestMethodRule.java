package com.iprogrammerr.bright.server.rule.method;

import com.iprogrammerr.bright.server.method.RequestMethod;

public final class SingleRequestMethodRule implements RequestMethodRule {

    private final RequestMethod method;

    public SingleRequestMethodRule(RequestMethod method) {
	this.method = method;
    }

    @Override
    public boolean isCompliant(String method) {
	return this.method.is(method);
    }

}
