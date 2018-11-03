package com.iprogrammerr.bright.server.response.template;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.template.LocationHeader;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;

public final class SeeOtherResponse extends ResponseEnvelope {

	private static final int CODE = 303;

	public SeeOtherResponse(String url, Header... headers) {
		super(new EmptyResponse(CODE, headers));
		super.headers().add(new LocationHeader(url));
	}

	public SeeOtherResponse(String url, List<Header> headers) {
		super(new EmptyResponse(CODE, headers));
		super.headers().add(new LocationHeader(url));
	}
}
