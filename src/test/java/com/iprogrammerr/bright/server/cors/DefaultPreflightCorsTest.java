package com.iprogrammerr.bright.server.cors;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.test.PreflightCorsRequest;

public final class DefaultPreflightCorsTest {

	@Test
	public void canIgnoreAllRequests() {
		assertThat(new DefaultPreflightCors(),
				new PreflightCorsThatCanRecognizeAndValidateRequests(new ArrayList<>(),
						Arrays.asList(new ParsedRequest("mock1", "put"), new PreflightCorsRequest("mock2"),
								new PreflightCorsRequest("mock3"))));
	}
}
