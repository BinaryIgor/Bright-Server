package com.iprogrammerr.bright.server.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.mock.RequestBinary;
import com.iprogrammerr.bright.server.mock.ResponseBinary;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public final class RequestResponseProtocolThatCanReadRequestAndWriteResponses extends TypeSafeMatcher<RequestResponseProtocol> {

	private final Request request;
	private final Response response;

	public RequestResponseProtocolThatCanReadRequestAndWriteResponses(Request request,
			Response response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(RequestResponseProtocol item, Description description) {
		description
				.appendText(String.format("%s that can read given request and write given response",
						getClass().getSimpleName()));
	}

	@Override
	protected boolean matchesSafely(RequestResponseProtocol item) {
		boolean matched = false;
		try {
			Request request = item
					.request(new ByteArrayInputStream(new RequestBinary(this.request).content()));
			matched = this.request.equals(request);
			if (matched) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				item.write(baos, this.response);
				matched = Arrays.equals(new ResponseBinary(this.response).content(),
						baos.toByteArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}
}
