package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.exception.PreConditionRequiredException;
import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.parser.FilterUrlPatternParser;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.rule.RequestMethodRule;

public class RequestFilter {

    private String urlPattern;
    private FilterUrlPatternParser urlPatternParser;
    private RequestMethodRule requestMethodRule;
    private ToFilterRequestHandler requestHandler;

    public RequestFilter(String urlPattern, RequestMethodRule requestMethodRule,
	    FilterUrlPatternParser urlPatternParser, ToFilterRequestHandler requestHandler) {
	this.urlPattern = urlPattern;
	this.requestMethodRule = requestMethodRule;
	this.urlPatternParser = urlPatternParser;
	this.requestHandler = requestHandler;
    }

    public boolean isPrimary() {
	return urlPatternParser.isPrimary(urlPattern);
    }

    public boolean shouldFilter(Request request) {
	if (!requestMethodRule.isCompliant(request.getMethod())) {
	    return false;
	}
	return urlPatternParser.isPrimary(urlPattern) || urlPatternParser.match(request.getPath(), urlPattern);
    }

    public Response filter(Request request) {
	if (!shouldFilter(request)) {
	    throw new PreConditionRequiredException("Request must be matched before it can be filtered");
	}
	return requestHandler.handle(request);
    }

}
