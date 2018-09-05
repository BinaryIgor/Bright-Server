package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.constants.HeaderValue;
import com.iprogrammerr.bright.server.constants.ResponseCode;

public class PlainTextResponse extends ResponseEnvelope {

    public PlainTextResponse(int responseCode, String text) {
	super(new ContentResponse(responseCode, HeaderValue.TEXT_PLAIN.getValue(), text.getBytes()));
    }

    public PlainTextResponse(ResponseCode responseCode, String text) {
	this(responseCode.getValue(), text);
    }

}
