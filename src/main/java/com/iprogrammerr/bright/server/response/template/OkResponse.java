package com.iprogrammerr.bright.server.response.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.ContentResponse;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class OkResponse extends ResponseEnvelope {

    private static final int CODE = 200;

    public OkResponse() {
	super(new EmptyResponse(CODE));
    }

    public OkResponse(String message) {
	super(new ContentResponse(CODE, message));
    }

    public OkResponse(ResponseBody body) {
	super(new ContentResponse(CODE, body));
    }

    public OkResponse(ResponseBody body, List<Header> headers) {
	super(new ContentResponse(CODE, body, headers));
    }

    public OkResponse(ResponseBody body, Header... headers) {
	this(body, new ArrayList<>(Arrays.asList(headers)));
    }

    public OkResponse(List<Header> headers) {
	super(new EmptyResponse(CODE, headers));
    }

}
