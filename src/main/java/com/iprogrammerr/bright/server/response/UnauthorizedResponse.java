package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.response.body.ResponseBody;

public class UnauthorizedResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 403;

    public UnauthorizedResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public UnauthorizedResponse(ResponseBody body) {
	super(new ContentResponse(RESPONSE_CODE, body));
    }

    public UnauthorizedResponse(String message) {
	super(new ContentResponse(RESPONSE_CODE, message));
    }

}
