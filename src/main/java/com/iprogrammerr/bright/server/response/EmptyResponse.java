package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.model.Header;

public class EmptyResponse implements Response {

    private int responseCode;

    public EmptyResponse(int responseCode) {
	this.responseCode = responseCode;
    }


    public EmptyResponse(ResponseCode responseCode) {
	this(responseCode.getValue());
    }

    @Override
    public int getResponseCode() {
	return responseCode;
    }

    @Override
    public List<Header> getHeaders() {
	return new ArrayList<>();
    }

    @Override
    public boolean hasBody() {
	return false;
    }

    @Override
    public byte[] getBody() {
	return new byte[0];
    }
}
