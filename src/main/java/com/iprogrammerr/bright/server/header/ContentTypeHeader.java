package com.iprogrammerr.bright.server.header;

public class ContentTypeHeader extends HeaderEnvelope {

    public ContentTypeHeader(String contentType) {
	super(new HttpHeader("Content-Type", contentType));
    }

}
