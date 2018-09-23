package com.iprogrammerr.bright.server.response.template;

import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;

public final class InternalServerErrorResponse extends ResponseEnvelope {

    private static final int CODE = 500;

    public InternalServerErrorResponse() {
	super(new EmptyResponse(CODE));
    }

    public InternalServerErrorResponse(String message) {
	super(new ContentResponse(CODE, message));
    }

}
