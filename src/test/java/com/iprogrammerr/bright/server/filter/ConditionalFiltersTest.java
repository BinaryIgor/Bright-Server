package com.iprogrammerr.bright.server.filter;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.response.BlockedResponse;
import com.iprogrammerr.bright.server.response.IntermediateResponse;
import com.iprogrammerr.bright.server.response.ToForwardResponse;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.rule.method.AnyRequestMethodRule;
import com.iprogrammerr.bright.server.rule.method.SingleRequestMethodRule;

public final class ConditionalFiltersTest {

	@Test
	public void canMatchFilters() {
		IntermediateResponse response = new BlockedResponse(new ForbiddenResponse());
		assertThat(
				new ConditionalFilters(
						Arrays.asList(new PotentialFilter("*", new AnyRequestMethodRule(), r -> response),
								new PotentialFilter("user/*", new AnyRequestMethodRule(), r -> response),
								new PotentialFilter("users", new SingleRequestMethodRule(new GetMethod()),
										r -> response))),
				new FiltersThatCanGiveIntermediateResponses(
						Arrays.asList(new ParsedRequest("user/1", "put"), new ParsedRequest("user/4", "get"),
								new ParsedRequest("admin", "delete"), new ParsedRequest("users", "get")),
						response));
	}

	@Test
	public void canForwardOnLackingFilter() {
		IntermediateResponse response = new ToForwardResponse();
		assertThat(
				new ConditionalFilters(
						Arrays.asList(new PotentialFilter("user/*", new AnyRequestMethodRule(), r -> response),
								new PotentialFilter("users", new SingleRequestMethodRule(new GetMethod()),
										r -> response))),
				new FiltersThatCanGiveIntermediateResponses(Arrays.asList(new ParsedRequest("admin/1", "put"),
						new ParsedRequest("admin/4", "post"), new ParsedRequest("admins", "get")), response));
	}

}
