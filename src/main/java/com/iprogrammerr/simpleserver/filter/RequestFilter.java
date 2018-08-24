package com.iprogrammerr.simpleserver.filter;

import com.iprogrammerr.simpleserver.constants.RequestMethod;

public abstract class RequestFilter implements RequestValidator {

    private RequestUrlRule requestUrlRule;
    private RequestMethodRule requestMethodRule;

    public RequestFilter(RequestUrlRule requestUrlRule, RequestMethodRule requestMethodRule) {
	this.requestUrlRule = requestUrlRule;
	this.requestMethodRule = requestMethodRule;
    }

    public boolean shouldFilter(String url, RequestMethod requestMethod) {
	return requestUrlRule.isCompliant(url) && requestMethodRule.isCompliant(requestMethod);
    }
}
