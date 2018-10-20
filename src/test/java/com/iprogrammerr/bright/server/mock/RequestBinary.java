package com.iprogrammerr.bright.server.mock;

import com.iprogrammerr.bright.server.binary.Binary;
import com.iprogrammerr.bright.server.request.Request;

public final class RequestBinary implements Binary {

    private final HttpBinary base;

    private RequestBinary(HttpBinary base) {
	this.base = base;
    }

    public RequestBinary(String protocol, Request source) {
	this(new HttpBinary(String.format("%s /%s %s", source.method(), source.url(), protocol), source.headers(),
		source.body()));
    }

    public RequestBinary(Request source) {
	this("HTTP/1.1", source);
    }

    @Override
    public byte[] content() throws Exception {
	return this.base.content();
    }

}
