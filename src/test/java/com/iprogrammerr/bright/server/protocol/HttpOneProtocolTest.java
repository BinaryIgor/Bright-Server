package com.iprogrammerr.bright.server.protocol;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.HttpHeader;
import com.iprogrammerr.bright.server.header.template.ContentLengthHeader;
import com.iprogrammerr.bright.server.header.template.ContentTypeHeader;
import com.iprogrammerr.bright.server.mock.MockedBinary;
import com.iprogrammerr.bright.server.mock.MockedMultipartBinary;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.response.body.JsonResponseBody;
import com.iprogrammerr.bright.server.response.template.CreatedResponse;

public final class HttpOneProtocolTest {

	private static final String HOST = "www.iprogrammerr.com:8080";
	private static final String PATH = "bright";

	@Test
	public void canReadAndWriteSimple() throws Exception {
		byte[] body = new MockedBinary().content();
		List<Header> headers = Arrays.asList(new HttpHeader("host", HOST), new ContentTypeHeader("image/jpeg"),
				new ContentLengthHeader(body.length));
		assertThat(new HttpOneProtocol(), new RequestResponseProtocolThatCanReadRequestAndWriteResponses(
				new ParsedRequest(PATH, "POST", headers, body), new CreatedResponse()));
	}

	@Test
	public void canReadAndWriteComplex() throws Exception {
		byte[] body = new MockedMultipartBinary().content();
		List<Header> headers = Arrays.asList(new HttpHeader("host", HOST), new ContentTypeHeader("multipart/mock-data"),
				new ContentLengthHeader(body.length));
		assertThat(new HttpOneProtocol(), new RequestResponseProtocolThatCanReadRequestAndWriteResponses(
				new ParsedRequest(PATH, "put", headers, body),
				new CreatedResponse(new JsonResponseBody("{\"status\": \"ok\"}"), new HttpHeader("Secret", "Secret"))));
	}

	@Test
	public void shouldClose() {
		assertThat(new HttpOneProtocol(), new RequestResponseProtocolThatCanDetermineConnectionState(
				new ParsedRequest(PATH, "put", Collections.singletonList(new HttpHeader("Connection", "Close"))),
				true));
	}

	@Test
	public void shouldKeepAlive() {
		assertThat(new HttpOneProtocol(), new RequestResponseProtocolThatCanDetermineConnectionState(
				new ParsedRequest(PATH, "put", Collections.singletonList(new HttpHeader("Connection", "Keep-alive"))),
				false));
	}

	@Test
	public void shouldCloseOnLackingHeader() {
		assertThat(new HttpOneProtocol(), new RequestResponseProtocolThatCanDetermineConnectionState(
				new ParsedRequest(PATH, "put", new ArrayList<>()), true));
	}
}
