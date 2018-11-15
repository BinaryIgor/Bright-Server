package com.iprogrammerr.bright.server.response;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.HttpHeader;
import com.iprogrammerr.bright.server.header.template.ContentLengthHeader;
import com.iprogrammerr.bright.server.header.template.ContentTypeHeader;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.test.MockedBinary;

public final class ContentResponseTest {

	@Test
	public void shouldHaveTypedBody() throws Exception {
		byte[] body = new MockedBinary().content();
		assertThat(new ContentResponse(201, new TypedResponseBody("image/jpg", body)), new ResponseThatHaveProperValues(
				201, Arrays.asList(new ContentTypeHeader("image/jpg"), new ContentLengthHeader(body.length)), body));
	}

	@Test
	public void shouldHavePlainTextBodyAndHeaders() {
		String body = "body";
		Header header = new HttpHeader("Authorization", "abcSecretDEF");
		assertThat(new ContentResponse(200, body, header),
				new ResponseThatHaveProperValues(200, Arrays.asList(new ContentTypeHeader("text/plain"),
						new ContentLengthHeader(body.getBytes().length), header), body.getBytes()));
	}
}
