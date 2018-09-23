package com.iprogrammerr.bright.server.response.template;

import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class NotFoundResponse extends ResponseEnvelope {

    private static final int CODE = 404;

    public NotFoundResponse() {
	super(new EmptyResponse(CODE));
    }

    public NotFoundResponse(String message) {
	super(new ContentResponse(CODE, message));
    }

    public NotFoundResponse(ResponseBody body) {
	super(new ContentResponse(CODE, body));
    }
}
