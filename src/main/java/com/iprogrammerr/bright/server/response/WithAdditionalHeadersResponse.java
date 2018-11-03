package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public final class WithAdditionalHeadersResponse extends ResponseEnvelope {

	public WithAdditionalHeadersResponse(Response base, List<Header> additionalHeaders) {
		super(base);
		base.headers().addAll(additionalHeaders);
	}
}
