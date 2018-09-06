package com.iprogrammerr.bright.server.header;

public class HtmlContentTypeHeader extends HeaderEnvelope{

    public HtmlContentTypeHeader() {
	super(new ContentTypeHeader("text/html"));
    }

}
