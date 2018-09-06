package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public class EmptyResponse implements Response {

    private int responseCode;
    private List<Header> headers;

    public EmptyResponse(int responseCode) {
	this(responseCode, new ArrayList<>());
    }

    public EmptyResponse(int responseCode, Header... headers) {
	this(responseCode, Arrays.asList(headers));
    }

    public EmptyResponse(int responseCode, List<Header> headers) {
	this.responseCode = responseCode;
	this.headers = headers;
    }

    @Override
    public int responseCode() {
	return responseCode;
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
