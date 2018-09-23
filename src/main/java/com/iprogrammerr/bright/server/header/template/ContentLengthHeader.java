package com.iprogrammerr.bright.server.header.template;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class ContentLengthHeader extends HeaderEnvelope {

    public ContentLengthHeader(int contentLength) {
	super(new HttpHeader("Content-Length", String.valueOf(contentLength)));
    }

}
