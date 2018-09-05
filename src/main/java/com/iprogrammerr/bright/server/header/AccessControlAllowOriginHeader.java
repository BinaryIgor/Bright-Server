package com.iprogrammerr.bright.server.header;

public class AccessControlAllowOriginHeader extends HeaderEnvelope{

    public AccessControlAllowOriginHeader(String value) {
	super(new HttpHeader("Access-Control-Allow-Origin", value));
    }

}
