package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.header.Header;

public class UnauthorizedResponse extends ResponseEnvelope {

    public UnauthorizedResponse() {
	super(new EmptyResponse(403));
    }

    public UnauthorizedResponse(Header contentTypeHeader, String body) {
	super(new ContentResponse(403, contentTypeHeader, body.getBytes()));
    }

}
