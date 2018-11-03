package com.iprogrammerr.bright.server.response.template;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class CreatedResponse extends ResponseEnvelope {

	private static final int CODE = 201;

	public CreatedResponse() {
		super(new EmptyResponse(CODE));
	}

	public CreatedResponse(String message, Header... headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public CreatedResponse(String message, List<Header> headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public CreatedResponse(ResponseBody body, Header... headers) {
		super(new ContentResponse(CODE, body, headers));
	}

	public CreatedResponse(ResponseBody body, List<Header> headers) {
		super(new ContentResponse(CODE, body, headers));
	}
}
