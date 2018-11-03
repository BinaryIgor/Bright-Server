package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public abstract class ResponseEnvelope implements Response {

	private final Response base;

	public ResponseEnvelope(Response base) {
		this.base = base;
	}

	@Override
	public final int code() {
		return this.base.code();
	}

	@Override
	public final List<Header> headers() {
		return this.base.headers();
	}

	@Override
	public final byte[] body() {
		return this.base.body();
	}
}
