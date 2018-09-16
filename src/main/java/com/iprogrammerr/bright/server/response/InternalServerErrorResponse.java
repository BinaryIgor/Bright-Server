package com.iprogrammerr.bright.server.response;

public class InternalServerErrorResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 500;

    public InternalServerErrorResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public InternalServerErrorResponse(String message) {
	super(new ContentResponse(RESPONSE_CODE, message));
    }

}
