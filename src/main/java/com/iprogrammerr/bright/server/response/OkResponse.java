package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public class OkResponse extends ResponseEnvelope {

    private static final int RESPONSE_CODE = 200;

    public OkResponse() {
	super(new EmptyResponse(RESPONSE_CODE));
    }

    public OkResponse(String message) {
	super(new ContentResponse(RESPONSE_CODE, message));
    }

    public OkResponse(ResponseBody body) {
	super(new ContentResponse(RESPONSE_CODE, body));
    }

    public OkResponse(ResponseBody body, List<Header> headers) {
	super(new ContentResponse(RESPONSE_CODE, body, headers));
    }

    public OkResponse(ResponseBody body, Header... headers) {
	this(body, new ArrayList<>(Arrays.asList(headers)));
    }

}
