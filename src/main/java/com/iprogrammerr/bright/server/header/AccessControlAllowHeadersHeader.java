package com.iprogrammerr.bright.server.header;

public class AccessControlAllowHeadersHeader extends HeaderEnvelope{

    public AccessControlAllowHeadersHeader(String value) {
	super(new HttpHeader("Access-Control-Allow-Headers", value));
    }

}
