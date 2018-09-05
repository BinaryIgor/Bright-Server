package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.header.HttpHeader;

public class EmptyResponse implements Response {

    private int responseCode;

    public EmptyResponse(int responseCode) {
	this.responseCode = responseCode;
    }


    public EmptyResponse(ResponseCode responseCode) {
	this(responseCode.getValue());
    }

    @Override
    public int responseCode() {
	return responseCode;
    }

    @Override
    public List<HttpHeader> headers() {
	return new ArrayList<>();
    }

    @Override
    public boolean hadBody() {
	return false;
    }

    @Override
    public byte[] body() {
	return new byte[0];
    }
}
