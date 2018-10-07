package com.iprogrammerr.bright.server.rule.filter;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.model.Mapping;
import com.iprogrammerr.bright.server.rule.method.RequestMethodRule;

public final class FilterRulesMapping implements Mapping<String[], Iterable<FilterRule>> {

    private final RequestMethodRule methodRule;

    public FilterRulesMapping(RequestMethodRule methodRule) {
	this.methodRule = methodRule;
    }

    @Override
    public Iterable<FilterRule> value(String[] from) {
	List<FilterRule> rules = new ArrayList<>();
	for (String f : from) {
	    rules.add(new ToFilterRequestRule(this.methodRule, f));
	}
	return rules;
    }

}
