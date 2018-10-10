package com.iprogrammerr.bright.server.request;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public final class ParsedRequest implements Request {

    private final String method;
    private String url;
    private final List<Header> headers;
    private final byte[] body;

    public ParsedRequest(String method, String url, List<Header> headers, byte[] body) {
	this.method = method;
	this.url = url;
	this.headers = headers;
	this.body = body;
    }

    public ParsedRequest(String method, String url, List<Header> headers) {
	this(method, url, headers, new byte[0]);
    }

    @Override
    public String url() {
	return this.url;
    }

    @Override
    public String method() {
	return this.method;
    }

    @Override
    public List<Header> headers() {
	return this.headers;
    }

    @Override
    public byte[] body() {
	return this.body;
    }

    @Override
    public boolean hasHeader(String key) {
	for (Header header : this.headers) {
	    if (header.is(key)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public String header(String key) {
	String header = "";
	for (Header h : this.headers) {
	    if (h.is(key)) {
		header = h.value();
		break;
	    }
	}
	return header;
    }

    @Override
    public void removeContext(String context) {
	if (this.url.startsWith(context) && !context.isEmpty()) {
	    url = url.replace(context + "/", "");
	}

    }

}
