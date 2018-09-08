package com.iprogrammerr.bright.server.header;

public class ContentEncodingHeader extends HeaderEnvelope {

    public ContentEncodingHeader(String encoding) {
	super(new HttpHeader("Content-Encoding", encoding));
    }

}
