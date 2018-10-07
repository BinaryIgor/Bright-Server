package com.iprogrammerr.bright.server.rule.method;

import com.iprogrammerr.bright.server.method.RequestMethod;

public final class ListOfRequestMethodRule implements RequestMethodRule {

    private final RequestMethod[] methods;

    public ListOfRequestMethodRule(RequestMethod... methods) {
	this.methods = methods;
    }

    @Override
    public boolean isCompliant(String method) {
	for (RequestMethod m : this.methods) {
	    if (m.is(method)) {
		return true;
	    }
	}
	return false;
    }

}
