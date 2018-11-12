package com.iprogrammerr.bright.server.rule;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.method.DeleteMethod;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.rule.filter.ToFilterRequestRule;
import com.iprogrammerr.bright.server.rule.method.ListOfRequestMethodRule;

public final class ToFilterRequestRuleTest {

	@Test
	public void canBeObserved() {
		assertThat(new ToFilterRequestRule(new ListOfRequestMethodRule(new GetMethod(), new PostMethod()), "user/*"),
				new RuleThatCanBeObserved<>(
						Arrays.asList(new ParsedRequest("user/1", "get"), new ParsedRequest("user/33", "post")),
						Arrays.asList(new ParsedRequest("user/1", "delete"),
								new ParsedRequest("user/search/1", "post"))));
	}

	@Test
	public void canBeObservedByEveryUrl() {
		assertThat(
				new ToFilterRequestRule(
						new ListOfRequestMethodRule(new GetMethod(), new PostMethod(), new DeleteMethod()), "*"),
				new RuleThatCanBeObserved<>(
						Arrays.asList(new ParsedRequest("user/1", "get"), new ParsedRequest("users", "post"),
								new ParsedRequest("riddle/4", "delete")),
						Arrays.asList(new ParsedRequest("user/1", "head"), new ParsedRequest("user/search/1", "put"))));
	}
}
