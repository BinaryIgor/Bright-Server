package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.exception.PreConditionRequiredException;
import com.iprogrammerr.bright.server.pattern.StarSymbolFilterUrlPattern;
import com.iprogrammerr.bright.server.pattern.ToFilterUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.rule.RequestMethodRule;

public class HttpRequestFilter implements ConditionalRequestFilter {

    private ToFilterUrlPattern urlPattern;
    private RequestMethodRule requestMethodRule;
    private RequestFilter requestFilter;

    public HttpRequestFilter(ToFilterUrlPattern urlPattern, RequestMethodRule requestMethodRule,
	    RequestFilter requestFilter) {
	this.urlPattern = urlPattern;
	this.requestMethodRule = requestMethodRule;
	this.requestFilter = requestFilter;
    }

    public HttpRequestFilter(String urlPattern, RequestMethodRule requestMethodRule, RequestFilter requestFilter) {
	this(new StarSymbolFilterUrlPattern(urlPattern), requestMethodRule, requestFilter);
    }

    @Override
    public boolean isPrimary() {
	return urlPattern.isPrimary();
    }

    @Override
    public boolean canFilter(Request request) {
	if (!requestMethodRule.isCompliant(request.method())) {
	    return false;
	}
	return urlPattern.isPrimary() || urlPattern.match(request.url());
    }

    @Override
    public Response filter(Request request) throws Exception {
	if (!canFilter(request)) {
	    throw new PreConditionRequiredException("Request must be matched before it can be filtered");
	}
	return requestFilter.filter(request);
    }

}
