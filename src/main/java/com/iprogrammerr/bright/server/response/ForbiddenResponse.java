package com.iprogrammerr.bright.server.response;

public class ForbiddenResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 403;

    public ForbiddenResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public ForbiddenResponse(String message) {
	super(new ContentResponse(RESPONSE_CODE, message));
    }

}
