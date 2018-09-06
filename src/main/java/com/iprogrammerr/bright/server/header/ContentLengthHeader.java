package com.iprogrammerr.bright.server.header;

public class ContentLengthHeader extends HeaderEnvelope{

    public ContentLengthHeader(int contentLength) {
	super(new HttpHeader("Content-Length", String.valueOf(contentLength)));
    }

}
