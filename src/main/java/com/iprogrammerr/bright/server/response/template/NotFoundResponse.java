package com.iprogrammerr.bright.server.response.template;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class NotFoundResponse extends ResponseEnvelope {

	private static final int CODE = 404;

	public NotFoundResponse(Header... headers) {
		super(new EmptyResponse(CODE, headers));
	}

	public NotFoundResponse(List<Header> headers) {
		super(new EmptyResponse(CODE, headers));
	}

	public NotFoundResponse(String message, Header... headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public NotFoundResponse(String message, List<Header> headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public NotFoundResponse(ResponseBody body, Header... headers) {
		super(new ContentResponse(CODE, body, headers));
	}

	public NotFoundResponse(ResponseBody body, List<Header> headers) {
		super(new ContentResponse(CODE, body, headers));
	}
}
