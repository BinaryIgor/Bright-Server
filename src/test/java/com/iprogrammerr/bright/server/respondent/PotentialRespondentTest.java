package com.iprogrammerr.bright.server.respondent;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.mock.MockedRequest;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class PotentialRespondentTest {

	@Test
	public void canRespond() throws Exception {
		Response response = new OkResponse();
		assertThat(new PotentialRespondent("user/{id:long}/game?minPoints=int", new GetMethod(), req -> response),
				new ConditionalRespondentThatCanRespond(
						Arrays.asList(new MockedRequest("user/1/game?minPoints=11", "get"),
								new MockedRequest("user/3/game?minPoints=44", "get"),
								new MockedRequest("user/99/game?minPoints=44&unnecessary=u", "get")),
						response));
	}

	@Test
	public void canRejectResponding() {
		assertThat(new PotentialRespondent("user/{id:long}", new GetMethod(), req -> new ForbiddenResponse()),
				new ConditionalRespondentThatCanRejectResponding(
						Arrays.asList(new MockedRequest("user/1", "put"), new MockedRequest("user/3", "delete"),
								new MockedRequest("user/99/game", "get"), new MockedRequest("game?id=4", "post"))));
	}

}
