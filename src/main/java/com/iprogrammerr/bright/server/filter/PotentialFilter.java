package com.iprogrammerr.bright.server.filter;

import java.util.Collections;
import java.util.List;

import com.iprogrammerr.bright.server.pattern.AsteriskFilterUrlPattern;
import com.iprogrammerr.bright.server.pattern.AsteriskFilterUrlPatternsMapping;
import com.iprogrammerr.bright.server.pattern.FilterUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.rule.RequestMethodRule;

public final class PotentialFilter implements ConditionalFilter {

    private final List<FilterUrlPattern> urlPatterns;
    private final RequestMethodRule methodRule;
    private final Filter filter;

    public PotentialFilter(List<FilterUrlPattern> urlPatterns, RequestMethodRule methodRule, Filter filter) {
	this.urlPatterns = urlPatterns;
	this.methodRule = methodRule;
	this.filter = filter;
    }

    public PotentialFilter(FilterUrlPattern urlPattern, RequestMethodRule methodRule, Filter filter) {
	this(Collections.singletonList(urlPattern), methodRule, filter);
    }

    public PotentialFilter(String urlPattern, RequestMethodRule methodRule, Filter filter) {
	this(new AsteriskFilterUrlPattern(urlPattern), methodRule, filter);
    }

    public PotentialFilter(RequestMethodRule methodRule, Filter filter, String... urlPatterns) {
	this(new AsteriskFilterUrlPatternsMapping().value(urlPatterns), methodRule, filter);
    }

    @Override
    public boolean isPrimary() {
	for (FilterUrlPattern up : this.urlPatterns) {
	    if (up.isPrimary()) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public boolean areConditionsMet(Request request) {
	return this.methodRule.isCompliant(request.method()) && (isPrimary() || isMatched(request.url()));
    }

    private boolean isMatched(String url) {
	for (FilterUrlPattern pattern : this.urlPatterns) {
	    if (pattern.isMatched(url)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public Response response(Request request) throws Exception {
	if (!areConditionsMet(request)) {
	    throw new Exception("Given request does not meet filter conditions");
	}
	return this.filter.response(request);
    }

}
