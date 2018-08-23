package com.iprogrammerr.simpleserver.model;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simpleserver.constants.ResponseCode;

public class Response {

    private final List<Header> headers = new ArrayList<>();
    private byte[] body;
    private ResponseCode code;

    public List<Header> getHeaders() {
	return headers;
    }

    public void setBody(byte[] body) {
	this.body = body;
    }

    public byte[] getBody() {
	return body;
    }

    public ResponseCode getCode() {
	return code;
    }

    public void addHeader(Header header) {
	headers.add(header);
    }

    public void setCode(ResponseCode code) {
	this.code = code;
    }

}
