package com.iprogrammerr.bright.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.filter.ConditionalFilter;
import com.iprogrammerr.bright.server.filter.Filter;
import com.iprogrammerr.bright.server.filter.PotentialFilter;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.mock.MockedRequest;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.template.OkResponse;
import com.iprogrammerr.bright.server.rule.filter.ToFilterRequestRule;
import com.iprogrammerr.bright.server.rule.method.ListOfRequestMethodRule;
import com.iprogrammerr.bright.server.rule.method.SingleRequestMethodRule;

public final class PotentialFilterTest {

    private static final Filter MOCKED_FILTER = req -> new OkResponse();

    @Test
    public void canMatchSingle() {
	ConditionalFilter filter = new PotentialFilter(new SingleRequestMethodRule(new GetMethod()), MOCKED_FILTER,
		"user/*/search");
	assertTrue(filter.areConditionsMet(new MockedRequest("user/1/search?scale=9.5", "get")));
	assertTrue(filter.areConditionsMet(new MockedRequest("user/23/search", "GET")));

    }

    @Test
    public void canMatchOneRuleToManyPatterns() {
	ConditionalFilter filter = new PotentialFilter(new ListOfRequestMethodRule(new GetMethod(), new PostMethod()),
		MOCKED_FILTER, "user/*", "game/*/search");
	Request request = new MockedRequest("user/1", "get");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("user/1", "post");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("game/444/search", "post");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("game/48/search", "get");
	assertTrue(filter.areConditionsMet(request));
    }

    @Test
    public void canRefuseMatches() {
	RequestMethod get = new GetMethod();
	RequestMethod post = new PostMethod();
	ConditionalFilter filter = new PotentialFilter(new ListOfRequestMethodRule(get, post), MOCKED_FILTER, "user/*",
		"game/*/search");
	Request request = new MockedRequest("game/1", "post");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("game/1", "get");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("user/1/abc", "get");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("user/3", "delete");
	assertFalse(filter.areConditionsMet(request));
	filter = new PotentialFilter(MOCKED_FILTER, new ToFilterRequestRule(new SingleRequestMethodRule(get), "user/*"),
		new ToFilterRequestRule(new SingleRequestMethodRule(post), "game/*/search"));
	request = new MockedRequest("user/1", "post");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("user/4/search", "put");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("game/444/search", "get");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("game/1", "post");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("game/1", "get");
	assertFalse(filter.areConditionsMet(request));
    }

    @Test
    public void canMatchManyRulesToManyPatterns() {
	ConditionalFilter filter = new PotentialFilter(MOCKED_FILTER,
		new ToFilterRequestRule(new SingleRequestMethodRule(new GetMethod()), "user/*"),
		new ToFilterRequestRule(new SingleRequestMethodRule(new PostMethod()), "game/*/search"));
	Request request = new MockedRequest("user/1", "get");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("game/444/search", "post");
	assertTrue(filter.areConditionsMet(request));
    }
}