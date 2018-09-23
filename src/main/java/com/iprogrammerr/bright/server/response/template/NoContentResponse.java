package com.iprogrammerr.bright.server.response.template;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;

public final class NoContentResponse extends ResponseEnvelope {

    private static final int CODE = 204;

    public NoContentResponse() {
	super(new EmptyResponse(CODE));
    }

    public NoContentResponse(Header... headers) {
	super(new EmptyResponse(CODE, headers));
    }

    public NoContentResponse(List<Header> headers) {
	super(new EmptyResponse(CODE, headers));
    }

}
