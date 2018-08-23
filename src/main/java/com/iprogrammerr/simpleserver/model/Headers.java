package com.iprogrammerr.simpleserver.model;

import java.util.List;

public class Headers {

    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private List<Header> headers;

    public Headers(List<Header> headers) {
	this.headers = headers;
    }

    public List<Header> getHeaders() {
	return headers;
    }

    public Header getContentLengthHeader() {
	for (Header header : headers) {
	    if (header.getKey().equals(CONTENT_LENGTH_HEADER_KEY)) {
		return header;
	    }
	}
	return null;
    }

    @Override
    public String toString() {
	return "Headers [headers=" + headers + "]";
    }

}
