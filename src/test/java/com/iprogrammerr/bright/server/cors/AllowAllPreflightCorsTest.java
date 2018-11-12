package com.iprogrammerr.bright.server.cors;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.bright.server.header.template.AccessControlAllowHeadersHeader;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowMethodsHeader;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowOriginHeader;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.test.PreflightCorsRequest;

public final class AllowAllPreflightCorsTest {

	@Test
	public void canDifferentiate() {
		assertThat(new AllowAllPreflightCors(),
				new PreflightCorsThatCanRecognizeAndValidateRequests(
						Arrays.asList(new PreflightCorsRequest("mockCors1", "domain1", "authorization, secret", "POST"),
								new PreflightCorsRequest("mockCors2")),
						Arrays.asList(new ParsedRequest("mock1", "put"), new ParsedRequest("mock2", "put"))));
	}

	@Test
	public void shouldHaveProperHeaders() {
		assertThat(new AllowAllPreflightCors().toAddHeaders(),
				Matchers.hasItems(new AccessControlAllowOriginHeader("*"), new AccessControlAllowHeadersHeader("*"),
						new AccessControlAllowMethodsHeader("*")));
	}
}
