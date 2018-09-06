package com.iprogrammerr.bright.server.header;

public class TextPlainContentTypeHeader extends HeaderEnvelope{

    public TextPlainContentTypeHeader() {
	super(new ContentTypeHeader("text/plain"));
    }

}
