package com.iprogrammerr.bright.server.response.template;

import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class BadRequestResponse extends ResponseEnvelope {

    private static final int CODE = 400;

    public BadRequestResponse() {
	super(new EmptyResponse(CODE));
    }

    public BadRequestResponse(ResponseBody responseBody) {
	super(new ContentResponse(CODE, responseBody));
    }

    public BadRequestResponse(String message) {
	super(new ContentResponse(CODE, message));
    }

}
