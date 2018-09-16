package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.response.body.ResponseBody;

public class BadRequestResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 400;

    public BadRequestResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public BadRequestResponse(ResponseBody responseBody) {
	super(new ContentResponse(RESPONSE_CODE, responseBody));
    }

    public BadRequestResponse(String message) {
	super(new ContentResponse(RESPONSE_CODE, message));
    }

}
