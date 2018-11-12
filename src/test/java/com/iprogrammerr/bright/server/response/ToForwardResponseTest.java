package com.iprogrammerr.bright.server.response;

import static org.junit.Assert.assertThat;

import org.junit.Test;

public final class ToForwardResponseTest {

	@Test
	public void canRespond() {
		assertThat(new ToForwardResponse(), new IntermediateResponseThatCanForwardAndBlock());
	}
}
