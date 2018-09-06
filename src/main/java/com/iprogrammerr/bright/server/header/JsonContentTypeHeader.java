package com.iprogrammerr.bright.server.header;

public class JsonContentTypeHeader extends HeaderEnvelope{

    public JsonContentTypeHeader() {
	super(new ContentTypeHeader("application/json"));
    }

}
