package com.iprogrammerr.simple.http.server.filter;

import com.iprogrammerr.simple.http.server.constants.RequestMethod;
import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.model.Response;

public class RequestFilter {

    private RequestUrlRule requestUrlRule;
    private RequestMethodRule requestMethodRule;
    private RequestValidator requestValidator;

    public RequestFilter(RequestUrlRule requestUrlRule, RequestMethodRule requestMethodRule,
	    RequestValidator requestValidator) {
	this.requestUrlRule = requestUrlRule;
	this.requestMethodRule = requestMethodRule;
	this.requestValidator = requestValidator;
    }

    public boolean shouldFilter(String url, RequestMethod requestMethod) {
	return requestUrlRule.isCompliant(url) && requestMethodRule.isCompliant(requestMethod);
    }

    public boolean filter(Request request, Response response) {
	return requestValidator.isValid(request, response);
    }

}
