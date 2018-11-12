package com.iprogrammerr.bright.server.response;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;

public final class BlockedResponseTest {

	@Test
	public void canRespond() {
		Response response = new ForbiddenResponse();
		assertThat(new BlockedResponse(response), new IntermediateResponseThatCanForwardAndBlock(response));
	}
}
