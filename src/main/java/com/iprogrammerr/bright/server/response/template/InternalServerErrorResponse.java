package com.iprogrammerr.bright.server.response.template;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class InternalServerErrorResponse extends ResponseEnvelope {

	private static final int CODE = 500;

	public InternalServerErrorResponse() {
		super(new EmptyResponse(CODE));
	}

	public InternalServerErrorResponse(String message, Header... headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public InternalServerErrorResponse(String message, List<Header> headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public InternalServerErrorResponse(ResponseBody body, Header... headers) {
		super(new ContentResponse(CODE, body, headers));
	}

	public InternalServerErrorResponse(ResponseBody body, List<Header> headers) {
		super(new ContentResponse(CODE, body, headers));
	}
}
