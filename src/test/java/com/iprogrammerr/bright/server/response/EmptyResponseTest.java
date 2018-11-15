package com.iprogrammerr.bright.server.response;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class EmptyResponseTest {

	@Test
	public void shouldBeEmpty() {
		assertThat(new EmptyResponse(400), new ResponseThatHaveProperValues(400));
	}

	@Test
	public void shouldHaveHeaders() {
		List<Header> headers = Arrays.asList(new HttpHeader("Authorization", "abc133"),
				new HttpHeader("Content_Type", "EMPTY"));
		assertThat(new EmptyResponse(200, headers), new ResponseThatHaveProperValues(200, headers));
	}
}
