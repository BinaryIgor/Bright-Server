package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.header.Header;

public class NotFoundResponse extends ResponseEnvelope {

    public NotFoundResponse() {
	super(new EmptyResponse(404));
    }

    public NotFoundResponse(Header contentTypeHeader, String body) {
	super(new ContentResponse(404, contentTypeHeader, body.getBytes()));
    }
}
