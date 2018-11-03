package com.iprogrammerr.bright.server.response.template;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class BadRequestResponse extends ResponseEnvelope {

	private static final int CODE = 400;

	public BadRequestResponse() {
		super(new EmptyResponse(CODE));
	}

	public BadRequestResponse(ResponseBody responseBody, Header... headers) {
		super(new ContentResponse(CODE, responseBody, headers));
	}

	public BadRequestResponse(ResponseBody responseBody, List<Header> headers) {
		super(new ContentResponse(CODE, responseBody, headers));
	}

	public BadRequestResponse(String message, Header... headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public BadRequestResponse(String message, List<Header> headers) {
		super(new ContentResponse(CODE, message, headers));
	}
}
