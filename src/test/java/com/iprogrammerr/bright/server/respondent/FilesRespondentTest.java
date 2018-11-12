package com.iprogrammerr.bright.server.respondent;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.OkResponse;
import com.iprogrammerr.bright.server.test.MockedBinary;

public final class FilesRespondentTest {

	private static final String CURRENT_PATH = new File(
			String.format("src%stest%sresources", File.separator, File.separator)).getAbsolutePath();

	@Test
	public void canRespond() throws Exception {
		Response response = new OkResponse(new TypedResponseBody("html", new MockedBinary().content()));
		assertThat(new FilesRespondent(CURRENT_PATH, r -> response),
				new ConditionalRespondentThatCanRespond(
						Arrays.asList(new ParsedRequest("index.html", "get"), new ParsedRequest("test.html", "get")),
						response));
	}

	@Test
	public void canRejectResponding() {
		assertThat(new FilesRespondent(CURRENT_PATH), new ConditionalRespondentThatCanRejectResponding(
				Arrays.asList(new ParsedRequest("index.txt", "get"), new ParsedRequest("test.jpg", "get"))));
	}
}