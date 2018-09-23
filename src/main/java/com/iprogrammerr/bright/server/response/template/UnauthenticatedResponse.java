package com.iprogrammerr.bright.server.response.template;

import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class UnauthenticatedResponse extends ResponseEnvelope {

    private static final int CODE = 401;

    public UnauthenticatedResponse() {
	super(new EmptyResponse(CODE));
    }

    public UnauthenticatedResponse(String message) {
	super(new ContentResponse(CODE, message));
    }

    public UnauthenticatedResponse(ResponseBody body) {
	super(new ContentResponse(CODE, body));
    }

}
