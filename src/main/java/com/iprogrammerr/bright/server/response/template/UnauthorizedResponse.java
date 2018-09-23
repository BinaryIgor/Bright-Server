package com.iprogrammerr.bright.server.response.template;

import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class UnauthorizedResponse extends ResponseEnvelope {

    private static final int CODE = 403;

    public UnauthorizedResponse() {
	super(new EmptyResponse(CODE));
    }

    public UnauthorizedResponse(ResponseBody body) {
	super(new ContentResponse(CODE, body));
    }

    public UnauthorizedResponse(String message) {
	super(new ContentResponse(CODE, message));
    }

}
