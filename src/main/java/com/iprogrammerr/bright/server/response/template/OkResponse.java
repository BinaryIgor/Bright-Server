package com.iprogrammerr.bright.server.response.template;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class OkResponse extends ResponseEnvelope {

	private static final int CODE = 200;

	public OkResponse(Header... headers) {
		super(new EmptyResponse(CODE, headers));
	}

	public OkResponse(List<Header> headers) {
		super(new EmptyResponse(CODE, headers));
	}

	public OkResponse(String message, Header... headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public OkResponse(String message, List<Header> headers) {
		super(new ContentResponse(CODE, message, headers));
	}

	public OkResponse(ResponseBody body, Header... headers) {
		super(new ContentResponse(CODE, body, headers));
	}

	public OkResponse(ResponseBody body, List<Header> headers) {
		super(new ContentResponse(CODE, body, headers));
	}
}
