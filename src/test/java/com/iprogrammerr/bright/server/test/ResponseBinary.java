package com.iprogrammerr.bright.server.test;

import com.iprogrammerr.bright.server.binary.Binary;
import com.iprogrammerr.bright.server.response.Response;

public final class ResponseBinary implements Binary {

	private final HttpBinary base;

	private ResponseBinary(HttpBinary base) {
		this.base = base;
	}

	public ResponseBinary(String protocol, Response source) {
		this(new HttpBinary(protocol + " " + source.code(), source.headers(), source.body()));
	}

	public ResponseBinary(Response source) {
		this("HTTP/1.1", source);
	}

	@Override
	public byte[] content() throws Exception {
		return this.base.content();
	}
}
