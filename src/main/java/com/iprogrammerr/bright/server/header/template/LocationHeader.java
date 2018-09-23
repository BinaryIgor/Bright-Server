package com.iprogrammerr.bright.server.header.template;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class LocationHeader extends HeaderEnvelope {

    public LocationHeader(String url) {
	super(new HttpHeader("Location", url));
    }

}
