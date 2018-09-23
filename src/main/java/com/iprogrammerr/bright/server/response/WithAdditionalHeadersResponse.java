package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public final class WithAdditionalHeadersResponse implements Response {

    private final Response base;

    public WithAdditionalHeadersResponse(Response base, List<Header> additionalHeaders) {
	this.base = base;
	this.base.headers().addAll(additionalHeaders);
    }

    @Override
    public int code() {
	return base.code();
    }

    @Override
    public List<Header> headers() {
	return base.headers();
    }

    @Override
    public boolean hasBody() {
	return base.hasBody();
    }

    @Override
    public byte[] body() {
	return base.body();
    }

}
