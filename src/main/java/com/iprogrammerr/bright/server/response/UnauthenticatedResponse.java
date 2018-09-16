package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.response.body.ResponseBody;

public class UnauthenticatedResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 401;

    public UnauthenticatedResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public UnauthenticatedResponse(String message) {
	super(new ContentResponse(RESPONSE_CODE, message));
    }

    public UnauthenticatedResponse(ResponseBody body) {
	super(new ContentResponse(RESPONSE_CODE, body));
    }

}
