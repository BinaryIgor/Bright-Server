package com.iprogrammerr.bright.server.respondent;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class PotentialRespondentTest {

	@Test
	public void canRespond() throws Exception {
		Response response = new OkResponse();
		assertThat(new PotentialRespondent("user/{id:long}/game?minPoints=int", new GetMethod(), req -> response),
				new ConditionalRespondentThatCanRespond(
						Arrays.asList(new ParsedRequest("user/1/game?minPoints=11", "get"),
								new ParsedRequest("user/3/game?minPoints=44", "get"),
								new ParsedRequest("user/99/game?minPoints=44&unnecessary=u", "get")),
						response));
	}

	@Test
	public void canRejectResponding() {
		assertThat(new PotentialRespondent("user/{id:long}", new GetMethod(), req -> new ForbiddenResponse()),
				new ConditionalRespondentThatCanRejectResponding(
						Arrays.asList(new ParsedRequest("user/1", "put"), new ParsedRequest("user/3", "delete"),
								new ParsedRequest("user/99/game", "get"), new ParsedRequest("game?id=4", "post"))));
	}
}
