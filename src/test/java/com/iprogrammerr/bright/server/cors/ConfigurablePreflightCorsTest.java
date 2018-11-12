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

public final class ConfigurablePreflightCorsTest {

	@Test
	public void canDifferentiate() {
		assertThat(new ConfigurablePreflightCors("domain1", "authorization, secret", "get, POST"),
				new PreflightCorsThatCanRecognizeAndValidateRequests(
						Arrays.asList(new PreflightCorsRequest("mockCors1", "domain1", "authorization, secret", "POST"),
								new PreflightCorsRequest("mockCors2", "domain1", "Authorization", "get")),
						Arrays.asList(new ParsedRequest("mock1", "put"), new ParsedRequest("mock", "put"))));
	}

	@Test
	public void shouldHaveProperHeaders() {
		assertThat(new ConfigurablePreflightCors("domain1", "authorization, secret", "get, post, put").toAddHeaders(),
				Matchers.hasItems(new AccessControlAllowOriginHeader("domain1"),
						new AccessControlAllowHeadersHeader("authorization, secret"),
						new AccessControlAllowMethodsHeader("get, post, put")));
	}
}
