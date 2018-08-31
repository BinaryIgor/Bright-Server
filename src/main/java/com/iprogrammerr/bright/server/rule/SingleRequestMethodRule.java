package com.iprogrammerr.bright.server.rule;

import com.iprogrammerr.bright.server.constants.RequestMethod;

public class SingleRequestMethodRule implements RequestMethodRule {

    private RequestMethod requestMethod;

    public SingleRequestMethodRule(RequestMethod requestMethod) {
	this.requestMethod = requestMethod;
    }

    @Override
    public boolean isCompliant(String requestMethod) {
	return this.requestMethod.equalsByValue(requestMethod);
    }

}
