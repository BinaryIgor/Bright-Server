package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public abstract class ResponseEnvelope implements Response {

    private final Response base;

    public ResponseEnvelope(Response base) {
	this.base = base;
    }

    @Override
    public final int responseCode() {
	return base.responseCode();
    }

    @Override
    public final List<Header> headers() {
	return base.headers();
    }

    @Override
    public final boolean hasBody() {
	return base.hasBody();
    }

    @Override
    public final byte[] body() {
	return base.body();
    }

}
