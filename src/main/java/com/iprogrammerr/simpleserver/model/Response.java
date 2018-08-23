package com.iprogrammerr.simpleserver.model;

import java.util.ArrayList;
import java.util.List;

public class Response {

    private final List<Header> headers = new ArrayList<>();
    private String body;
    private int code;

    public List<Header> getHeaders() {
	return headers;
    }

    public String getBody() {
	return body;
    }

    public int getCode() {
	return code;
    }

    public void addHeader(Header header) {
	headers.add(header);
    }

    public void setCode(int responseCode) {
	this.code = responseCode;
    }

}
