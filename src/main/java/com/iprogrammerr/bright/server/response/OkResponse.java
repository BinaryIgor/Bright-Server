package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public class OkResponse extends ResponseEnvelope {

    public OkResponse() {
	super(new EmptyResponse(200));
    }

    public OkResponse(Header contentTypeHeader, byte[] body) {
	this(contentTypeHeader, body, new ArrayList<>());
    }

    public OkResponse(Header contentTypeHeader, String body) {
	this(contentTypeHeader, body.getBytes());
    }

    public OkResponse(Header contentTypeHeader, byte[] body, List<Header> headers) {
	super(new ContentResponse(200, contentTypeHeader, body, headers));
    }

    public OkResponse(Header contentTypeHeader, byte[] body, Header... headers) {
	this(contentTypeHeader, body, Arrays.asList(headers));
    }

}
