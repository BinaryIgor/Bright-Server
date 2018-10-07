package com.iprogrammerr.bright.server.rule;

import com.iprogrammerr.bright.server.pattern.AsteriskFilterUrlPattern;
import com.iprogrammerr.bright.server.pattern.FilterUrlPattern;
import com.iprogrammerr.bright.server.request.Request;

public final class FilterRule implements RequestRule {

    private final RequestMethodRule methodRule;
    private final FilterUrlPattern urlPattern;

    public FilterRule(RequestMethodRule methodRule, FilterUrlPattern urlPattern) {
	this.methodRule = methodRule;
	this.urlPattern = urlPattern;
    }

    public FilterRule(RequestMethodRule methodRule, String urlPattern) {
	this(methodRule, new AsteriskFilterUrlPattern(urlPattern));
    }

    @Override
    public boolean isCompliant(Request value) {
	return this.methodRule.isCompliant(value.method()) && urlPattern.isMatched(value.url());
    }

}
