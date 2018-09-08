package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public class CreatedResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 201;

    public CreatedResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public CreatedResponse(Header contentTypeHeader, String body) {
	this(contentTypeHeader, body.getBytes());
    }

    public CreatedResponse(Header contentTypeHeader, byte[] body) {
	super(new ContentResponse(RESPONSE_CODE, contentTypeHeader, body));
    }

    public CreatedResponse(Header contentTypeHeader, byte[] body, List<Header> headers) {
	super(new ContentResponse(RESPONSE_CODE, contentTypeHeader, body, headers));
    }

    public CreatedResponse(Header contentTypeHeader, byte[] body, Header... headers) {
	this(contentTypeHeader, body, new ArrayList<>(Arrays.asList(headers)));
    }

}
