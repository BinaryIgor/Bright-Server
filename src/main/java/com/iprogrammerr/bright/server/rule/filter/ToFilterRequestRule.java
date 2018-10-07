package com.iprogrammerr.bright.server.rule.filter;

import com.iprogrammerr.bright.server.pattern.AsteriskFilterUrlPattern;
import com.iprogrammerr.bright.server.pattern.FilterUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.rule.method.RequestMethodRule;

public final class ToFilterRequestRule implements FilterRule {

    private final RequestMethodRule methodRule;
    private final FilterUrlPattern urlPattern;

    public ToFilterRequestRule(RequestMethodRule methodRule, FilterUrlPattern urlPattern) {
	this.methodRule = methodRule;
	this.urlPattern = urlPattern;
    }

    public ToFilterRequestRule(RequestMethodRule methodRule, String urlPattern) {
	this(methodRule, new AsteriskFilterUrlPattern(urlPattern));
    }

    @Override
    public boolean isCompliant(Request value) {
	return this.methodRule.isCompliant(value.method()) && this.urlPattern.isMatched(value.url());
    }

    @Override
    public boolean isPrimary() {
	return this.urlPattern.isPrimary();
    }

}
