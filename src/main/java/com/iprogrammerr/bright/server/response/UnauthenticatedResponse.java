package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.header.TextPlainContentTypeHeader;

public class UnauthenticatedResponse extends ResponseEnvelope {

    public UnauthenticatedResponse() {
	super(new EmptyResponse(401));
    }

    public UnauthenticatedResponse(String message) {
	super(new ContentResponse(401, new TextPlainContentTypeHeader(), message.getBytes()));
    }

}
