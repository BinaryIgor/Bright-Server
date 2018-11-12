package com.iprogrammerr.bright.server.filter;

import java.util.Arrays;
import java.util.Collections;

import com.iprogrammerr.bright.server.pattern.AsteriskFilterUrlPattern;
import com.iprogrammerr.bright.server.pattern.FilterUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.IntermediateResponse;
import com.iprogrammerr.bright.server.rule.filter.FilterRule;
import com.iprogrammerr.bright.server.rule.filter.FilterRulesMapping;
import com.iprogrammerr.bright.server.rule.filter.ToFilterRequestRule;
import com.iprogrammerr.bright.server.rule.method.RequestMethodRule;

public final class PotentialFilter implements ConditionalFilter {

	private final Iterable<FilterRule> filterRules;
	private final Filter filter;

	public PotentialFilter(Iterable<FilterRule> filterRules, Filter filter) {
		this.filterRules = filterRules;
		this.filter = filter;
	}

	public PotentialFilter(Filter filter, FilterRule... filterRules) {
		this(Arrays.asList(filterRules), filter);
	}

	public PotentialFilter(FilterUrlPattern urlPattern, RequestMethodRule methodRule, Filter filter) {
		this(Collections.singletonList(new ToFilterRequestRule(methodRule, urlPattern)), filter);
	}

	public PotentialFilter(String urlPattern, RequestMethodRule methodRule, Filter filter) {
		this(new AsteriskFilterUrlPattern(urlPattern), methodRule, filter);
	}

	public PotentialFilter(RequestMethodRule methodRule, Filter filter, String... urlPatterns) {
		this(new FilterRulesMapping(methodRule).value(urlPatterns), filter);
	}

	@Override
	public boolean isPrimary() {
		for (FilterRule fr : this.filterRules) {
			if (fr.isPrimary()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean areConditionsMet(Request request) {
		for (FilterRule fr : this.filterRules) {
			if (fr.isCompliant(request)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public IntermediateResponse response(Request request) throws Exception {
		if (!areConditionsMet(request)) {
			throw new Exception("Given request does not meet filter conditions");
		}
		return this.filter.response(request);
	}
}
