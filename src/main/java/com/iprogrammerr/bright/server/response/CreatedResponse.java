package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public class CreatedResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 201;

    public CreatedResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public CreatedResponse(ResponseBody body) {
	super(new ContentResponse(RESPONSE_CODE, body));
    }

    public CreatedResponse(String message) {
	super(new ContentResponse(RESPONSE_CODE, message));
    }

    public CreatedResponse(ResponseBody body, List<Header> headers) {
	super(new ContentResponse(RESPONSE_CODE, body, headers));
    }

}
