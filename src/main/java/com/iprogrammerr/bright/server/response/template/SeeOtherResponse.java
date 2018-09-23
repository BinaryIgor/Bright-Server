package com.iprogrammerr.bright.server.response.template;

import com.iprogrammerr.bright.server.header.template.LocationHeader;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.ResponseEnvelope;

public final class SeeOtherResponse extends ResponseEnvelope {

    public SeeOtherResponse(String url) {
	super(new EmptyResponse(303, new LocationHeader(url)));
    }

}
