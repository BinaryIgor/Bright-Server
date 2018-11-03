package com.iprogrammerr.bright.server.filter;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.mock.MockedRequest;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.ToForwardResponse;
import com.iprogrammerr.bright.server.rule.filter.ToFilterRequestRule;
import com.iprogrammerr.bright.server.rule.method.ListOfRequestMethodRule;
import com.iprogrammerr.bright.server.rule.method.SingleRequestMethodRule;

public final class PotentialFilterTest {

	private static final Filter MOCKED_FILTER = req -> new ToForwardResponse();

	@Test
	public void canMatchSingleRule() {
		List<Request> requests = new ArrayList<>();
		requests.add(new MockedRequest("user/1/search?scale=9.5", "get"));
		requests.add(new MockedRequest("user/23/search", "GET"));
		assertThat(new PotentialFilter(new SingleRequestMethodRule(new GetMethod()), MOCKED_FILTER,
				"user/*/search"), new PotentialFilterThatCanMatchAndRefuse(requests));
	}

	@Test
	public void canMatchOneRuleToManyPatterns() {
		List<Request> requests = new ArrayList<>();
		requests.add(new MockedRequest("user/1", "get"));
		requests.add(new MockedRequest("user/1", "post"));
		requests.add(new MockedRequest("game/444/search", "post"));
		requests.add(new MockedRequest("game/48/search", "get"));
		assertThat(
				new PotentialFilter(new ListOfRequestMethodRule(new GetMethod(), new PostMethod()),
						MOCKED_FILTER, "user/*", "game/*/search"),
				new PotentialFilterThatCanMatchAndRefuse(requests));
	}

	@Test
	public void canMatchManyRulesToManyPatterns() {
		List<Request> requests = new ArrayList<>();
		requests.add(new MockedRequest("user/1", "get"));
		requests.add(new MockedRequest("game/444/search", "post"));
		assertThat(
				new PotentialFilter(MOCKED_FILTER,
						new ToFilterRequestRule(new SingleRequestMethodRule(new GetMethod()),
								"user/*"),
						new ToFilterRequestRule(new SingleRequestMethodRule(new PostMethod()),
								"game/*/search")),
				new PotentialFilterThatCanMatchAndRefuse(requests));
	}

	@Test
	public void canRefuseMatches() {
		List<Request> requests = new ArrayList<>();
		requests.add(new MockedRequest("game/1", "post"));
		requests.add(new MockedRequest("game/1", "get"));
		requests.add(new MockedRequest("user/1/abc", "get"));
		requests.add(new MockedRequest("user/3", "delete"));
		assertThat(
				new PotentialFilter(new ListOfRequestMethodRule(new GetMethod(), new PostMethod()),
						MOCKED_FILTER, "user/*", "game/*/search"),
				new PotentialFilterThatCanMatchAndRefuse(requests, true));
	}
}