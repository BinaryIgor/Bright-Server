package com.iprogrammerr.bright.server.request;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public abstract class RequestEnvelope implements Request{

    private Request request;
    
    public RequestEnvelope(Request request) {
	this.request = request;
    }

    @Override
    public String url() {
	return request.url();
    }

    @Override
    public String method() {
	return request.method();
    }

    @Override
    public List<Header> headers() {
	return request.headers();
    }

    @Override
    public boolean hasHeader(String key) {
	return request.hasHeader(key);
    }

    @Override
    public String header(String key) {
	return request.header(key);
    }

    @Override
    public byte[] body() {
	return request.body();
    }

    @Override
    public void removeContextPath(String contextPath) {
	request.body();
    }
}
