package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public final class EmptyResponse implements Response {

    private final int code;
    private final List<Header> headers;

    public EmptyResponse(int code) {
	this(code, new ArrayList<>());
    }

    public EmptyResponse(int code, Header... headers) {
	this(code, new ArrayList<>(Arrays.asList(headers)));
    }

    public EmptyResponse(int code, List<Header> headers) {
	this.code = code;
	this.headers = headers;
    }

    @Override
    public int code() {
	return code;
    }

    @Override
    public List<Header> headers() {
	return headers;
    }

    @Override
    public boolean hasBody() {
	return false;
    }

    @Override
    public byte[] body() {
	return new byte[0];
    }
}
