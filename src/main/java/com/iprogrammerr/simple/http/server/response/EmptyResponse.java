package com.iprogrammerr.simple.http.server.response;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.HeaderValue;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.constants.ResponseHeaderKey;
import com.iprogrammerr.simple.http.server.model.Header;

public class EmptyResponse implements Response {

    private ResponseCode responseCode;

    public EmptyResponse() {
	this.responseCode = ResponseCode.NOT_FOUND;
    }

    public EmptyResponse(ResponseCode responseCode) {
	this.responseCode = responseCode;
    }

    @Override
    public ResponseCode getResponseCode() {
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

    @Override
    public Header getContentTypeHeader() {
	return new Header(ResponseHeaderKey.CONTENT_TYPE, HeaderValue.TEXT_PLAIN);
    }

}
