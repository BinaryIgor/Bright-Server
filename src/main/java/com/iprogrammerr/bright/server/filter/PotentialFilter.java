package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.pattern.StarSymbolFilterUrlPattern;
import com.iprogrammerr.bright.server.pattern.ToFilterUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.rule.RequestMethodRule;

public final class PotentialFilter implements ConditionalFilter {

    private final ToFilterUrlPattern urlPattern;
    private final RequestMethodRule requestMethodRule;
    private final Filter requestFilter;

    public PotentialFilter(ToFilterUrlPattern urlPattern, RequestMethodRule requestMethodRule,
	    Filter requestFilter) {
	this.urlPattern = urlPattern;
	this.requestMethodRule = requestMethodRule;
	this.requestFilter = requestFilter;
    }

    public PotentialFilter(String urlPattern, RequestMethodRule requestMethodRule, Filter requestFilter) {
	this(new StarSymbolFilterUrlPattern(urlPattern), requestMethodRule, requestFilter);
    }

    @Override
    public boolean isPrimary() {
	return urlPattern.isPrimary();
    }

    @Override
    public boolean areConditionsMet(Request request) {
	return requestMethodRule.isCompliant(request.method())
		&& (urlPattern.isPrimary() || urlPattern.isMatched(request.url()));
    }

    @Override
    public Response filtered(Request request) throws Exception {
	if (!areConditionsMet(request)) {
	    throw new Exception("Given request does not meet filter conditions");
	}
	return requestFilter.filtered(request);
    }

}
