package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public final class EmptyResponse implements Response {

    private final int code;
    private final List<Header> headers;
    private final byte[] body;

    public EmptyResponse(int code) {
	this(code, new ArrayList<>());
    }

    public EmptyResponse(int code, Header... headers) {
	this(code, new ArrayList<>(Arrays.asList(headers)));
    }

    public EmptyResponse(int code, List<Header> headers) {
	this.code = code;
	this.headers = headers;
	this.body = new byte[0];
    }

    @Override
    public int code() {
	return this.code;
    }

    @Override
    public List<Header> headers() {
	return this.headers;
    }

    @Override
    public boolean hasBody() {
	return false;
    }

    @Override
    public byte[] body() {
	return this.body;
    }
}
