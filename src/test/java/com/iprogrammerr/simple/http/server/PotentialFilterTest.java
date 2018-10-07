package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.filter.ConditionalFilter;
import com.iprogrammerr.bright.server.filter.PotentialFilter;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.template.OkResponse;
import com.iprogrammerr.bright.server.rule.filter.ToFilterRequestRule;
import com.iprogrammerr.bright.server.rule.method.ListOfRequestMethodRule;
import com.iprogrammerr.bright.server.rule.method.SingleRequestMethodRule;
import com.iprogrammerr.simple.http.server.mock.MockedRequest;

public class PotentialFilterTest {

    @Test
    public void canMatchSingle() {
	ConditionalFilter filter = new PotentialFilter(new SingleRequestMethodRule(new GetMethod()),
		req -> new OkResponse(), "user/*/search");
	Request request = new MockedRequest("user/1/search?scale=9.5", "get");
	assertTrue(filter.areConditionsMet(request));

    }

    @Test
    public void canMatchOneRuleToManyPatterns() {
	ConditionalFilter filter = new PotentialFilter(new ListOfRequestMethodRule(new GetMethod(), new PostMethod()),
		req -> new OkResponse(), "user/*", "game/*/search");
	Request request = new MockedRequest("user/1", "get");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("user/1", "post");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("game/444/search", "post");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("game/1", "post");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("game/1", "get");
	assertFalse(filter.areConditionsMet(request));
    }

    @Test
    public void canMatchManyRulesToManyPatterns() {
	RequestMethod get = new GetMethod();
	RequestMethod post = new PostMethod();
	ConditionalFilter filter = new PotentialFilter(req -> new OkResponse(),
		new ToFilterRequestRule(new SingleRequestMethodRule(get), "user/*"),
		new ToFilterRequestRule(new SingleRequestMethodRule(post), "game/*/search"));
	Request request = new MockedRequest("user/1", "get");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("user/1", "post");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("game/444/search", "get");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("game/444/search", "post");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("game/1", "post");
	assertFalse(filter.areConditionsMet(request));
	request = new MockedRequest("game/1", "get");
	assertFalse(filter.areConditionsMet(request));
    }
}