package com.iprogrammerr.bright.server.response.template;

import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class ForbiddenResponse extends ResponseEnvelope {

    private static final int CODE = 403;

    public ForbiddenResponse() {
	super(new EmptyResponse(CODE));
    }

    public ForbiddenResponse(String message) {
	super(new ContentResponse(CODE, message));
    }

    public ForbiddenResponse(ResponseBody body) {
	super(new ContentResponse(CODE, body));
    }

}
