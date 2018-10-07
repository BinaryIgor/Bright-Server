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
import com.iprogrammerr.bright.server.rule.ListOfRequestMethodRule;
import com.iprogrammerr.simple.http.server.mock.MockedRequest;

public class PotentialFilterTest {

    @Test
    public void canMatchMultiple() {
	RequestMethod get = new GetMethod();
	RequestMethod post = new PostMethod();
	ConditionalFilter filter = new PotentialFilter(new ListOfRequestMethodRule(get, post), req -> new OkResponse(),
		"user/*", "game/*/search");
	Request request = new MockedRequest("user/1", "get");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("game/444/search", "post");
	assertTrue(filter.areConditionsMet(request));
	request = new MockedRequest("game/1", "post");
	assertFalse(filter.areConditionsMet(request));
    }
}