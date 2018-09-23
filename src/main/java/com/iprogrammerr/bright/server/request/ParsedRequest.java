package com.iprogrammerr.bright.server.request;

import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.exception.NotFoundException;
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
	return url;
    }

    @Override
    public String method() {
	return method;
    }

    @Override
    public List<Header> headers() {
	return headers;
    }

    @Override
    public byte[] body() {
	return body;
    }

    @Override
    public boolean hasHeader(String key) {
	for (Header header : headers) {
	    if (header.is(key)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public String header(String key) throws Exception {
	for (Header header : headers) {
	    if (header.is(key)) {
		return header.value();
	    }
	}
	throw new NotFoundException();
    }

    @Override
    public void removeContextPath(String contextPath) {
	if (url.startsWith(contextPath) && !contextPath.isEmpty()) {
	    url = url.replace(contextPath + "/", "");
	}

    }

    @Override
    public String toString() {
	return "ParsedRequest [method=" + method + ", url=" + url + ", headers=" + headers + ", body="
		+ Arrays.toString(body) + "]";
    }

}
