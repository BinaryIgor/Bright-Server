package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.response.body.ResponseBody;

public class NotFoundResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 404;

    public NotFoundResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public NotFoundResponse(String message) {
	super(new ContentResponse(RESPONSE_CODE, message));
    }

    public NotFoundResponse(ResponseBody body) {
	super(new ContentResponse(RESPONSE_CODE, body));
    }
}
