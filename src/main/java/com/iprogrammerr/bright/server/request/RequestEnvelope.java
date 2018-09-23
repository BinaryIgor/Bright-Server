package com.iprogrammerr.bright.server.request;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public abstract class RequestEnvelope implements Request {

    private final Request base;

    public RequestEnvelope(Request base) {
	this.base = base;
    }

    @Override
    public final String url() {
	return base.url();
    }

    @Override
    public final String method() {
	return base.method();
    }

    @Override
    public final List<Header> headers() {
	return base.headers();
    }

    @Override
    public final boolean hasHeader(String key) {
	return base.hasHeader(key);
    }

    @Override
    public final String header(String key) throws Exception {
	return base.header(key);
    }

    @Override
    public byte[] body() {
	return base.body();
    }

    @Override
    public void removeContextPath(String contextPath) {
	base.removeContextPath(contextPath);
    }
}
