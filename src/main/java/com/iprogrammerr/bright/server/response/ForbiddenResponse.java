package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.header.Header;

public class ForbiddenResponse extends ResponseEnvelope {

    public ForbiddenResponse() {
	super(new EmptyResponse(403));
    }
    
    public ForbiddenResponse(Header contentTypeHeader, String body) {
	super(new ContentResponse(403, contentTypeHeader, body.getBytes()));
    }

}
