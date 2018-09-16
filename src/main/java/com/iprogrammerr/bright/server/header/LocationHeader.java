package com.iprogrammerr.bright.server.header;

public class LocationHeader extends HeaderEnvelope {

    public LocationHeader(String url) {
	super(new HttpHeader("Location", url));
    }

}
