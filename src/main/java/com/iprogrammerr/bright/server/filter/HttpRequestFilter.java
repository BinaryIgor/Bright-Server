package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.pattern.StarSymbolFilterUrlPattern;
import com.iprogrammerr.bright.server.pattern.ToFilterUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.rule.RequestMethodRule;

public final class HttpRequestFilter implements ConditionalRequestFilter {

    private final ToFilterUrlPattern urlPattern;
    private final RequestMethodRule requestMethodRule;
    private final RequestFilter requestFilter;

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
    public boolean primary() {
	return urlPattern.primary();
    }

    @Override
    public boolean conditionsMet(Request request) {
	return requestMethodRule.compliant(request.method())
		&& (urlPattern.primary() || urlPattern.match(request.url()));
    }

    @Override
    public Response filter(Request request) throws Exception {
	if (!conditionsMet(request)) {
	    throw new Exception("Given request does not meet filter conditions");
	}
	return requestFilter.filter(request);
    }

}
