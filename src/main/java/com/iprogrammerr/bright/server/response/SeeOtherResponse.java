package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.header.LocationHeader;

public class SeeOtherResponse extends ResponseEnvelope {

    public SeeOtherResponse(String url) {
	super(new EmptyResponse(303, new LocationHeader(url)));
    }

}
