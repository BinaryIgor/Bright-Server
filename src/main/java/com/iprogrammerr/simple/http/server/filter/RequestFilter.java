package com.iprogrammerr.simple.http.server.filter;

import com.iprogrammerr.simple.http.server.exception.PreConditionRequiredException;
import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.parser.FilterUrlPatternParser;
import com.iprogrammerr.simple.http.server.resolver.RequestHandler;
import com.iprogrammerr.simple.http.server.response.Response;
import com.iprogrammerr.simple.http.server.rule.RequestMethodRule;

public class RequestFilter {

    private String urlPattern;
    private FilterUrlPatternParser urlPatternParser;
    private RequestMethodRule requestMethodRule;
    private RequestHandler requestHandler;
    private boolean readyToFilter;

    public RequestFilter(String urlPattern, RequestMethodRule requestMethodRule,
	    FilterUrlPatternParser urlPatternParser, RequestHandler requestHandler) {
	this.urlPattern = urlPattern;
	this.requestMethodRule = requestMethodRule;
	this.urlPatternParser = urlPatternParser;
	this.requestHandler = requestHandler;
    }

    public boolean isPrimary() {
	return urlPatternParser.isPrimary(urlPattern);
    }

    public boolean shouldFilter(Request request) {
	readyToFilter = false;
	if (!requestMethodRule.isCompliant(request.getMethod())) {
	    return false;
	}
	readyToFilter = urlPatternParser.match(request.getPath(), urlPattern);
	return readyToFilter;
    }

    public Response filter(Request request) {
	if (!isPrimary() && !readyToFilter) {
	    throw new PreConditionRequiredException("Request must be matched before it can be filtered");
	}
	readyToFilter = false;
	return requestHandler.handle(request);
    }

}
