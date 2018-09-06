package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.header.TextPlainContentTypeHeader;

public class PlainTextResponse extends ResponseEnvelope {

    public PlainTextResponse(int responseCode, String text) {
	super(new ContentResponse(responseCode, new TextPlainContentTypeHeader(), text.getBytes()));
    }


}
