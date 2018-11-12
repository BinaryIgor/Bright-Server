package com.iprogrammerr.bright.server;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import com.iprogrammerr.bright.server.application.HttpApplication;
import com.iprogrammerr.bright.server.filter.PotentialFilter;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.PutMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.respondent.PotentialRespondent;
import com.iprogrammerr.bright.server.response.BlockedResponse;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;
import com.iprogrammerr.bright.server.rule.method.AnyRequestMethodRule;

public final class HttpApplicationTest {

	@Test
	public void canRespond() {
		RequestMethod get = new GetMethod();
		Response response = new OkResponse();
		assertThat(
				new HttpApplication(Arrays.asList(new PotentialRespondent("user/{id:int}", get, r -> response),
						new PotentialRespondent("users", get, r -> response))),
				new ApplicationThatCanRespond(
						Arrays.asList(new ParsedRequest("user/1", "get"), new ParsedRequest("users", "get")),
						response));
	}

	@Test
	public void canFilter() {
		Response response = new ForbiddenResponse();
		assertThat(new HttpApplication(
				Collections.singletonList(
						new PotentialRespondent("user/{id:int}", new PostMethod(), r -> new OkResponse())),
				Arrays.asList(
						new PotentialFilter("user/*", new AnyRequestMethodRule(), r -> new BlockedResponse(response)))),
				new ApplicationThatCanRespond(Collections.singletonList(new ParsedRequest("user/1", "post")),
						response));
	}

	@Test
	public void canReturnNotFound() {
		assertThat(
				new HttpApplication(Collections.singletonList(
						new PotentialRespondent("user/{id:int}", new PutMethod(), r -> new OkResponse()))),
				new ApplicationThatCanRespond(Collections.singletonList(new ParsedRequest("riddle/1", "post")),
						new NotFoundResponse()));
	}

	@Test
	public void canWithdrawFromResponding() {
		assertThat(
				new HttpApplication("riddle",
						Collections.singletonList(
								new PotentialRespondent("user/{id:int}", new PostMethod(), r -> new OkResponse()))),
				new ApplicationThatCanWithdrawFromResponding(
						Arrays.asList(new ParsedRequest("user/1", "post"), new ParsedRequest("oblivion", "get"))));
	}
}
