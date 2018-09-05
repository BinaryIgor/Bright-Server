package com.iprogrammerr.bright.server.header;

public class AccessControlAllowMethodsHeader extends HeaderEnvelope {

    public AccessControlAllowMethodsHeader(String value) {
	super(new HttpHeader("Access-Control-Allow-Methods", value));
    }
}
