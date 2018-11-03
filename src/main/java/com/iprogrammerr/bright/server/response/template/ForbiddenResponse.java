package com.iprogrammerr.bright.server.response.template;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class ForbiddenResponse extends ResponseEnvelope {

	private static final int CODE = 403;

	public ForbiddenResponse() {
		super(new EmptyResponse(CODE));
	}

	public ForbiddenResponse(String message, Header... headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public ForbiddenResponse(String message, List<Header> headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public ForbiddenResponse(ResponseBody body, Header... headers) {
		super(new ContentResponse(CODE, body, headers));
	}

	public ForbiddenResponse(ResponseBody body, List<Header> headers) {
		super(new ContentResponse(CODE, body, headers));
	}
}
