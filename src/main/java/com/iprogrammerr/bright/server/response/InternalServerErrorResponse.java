package com.iprogrammerr.bright.server.response;

public class InternalServerErrorResponse extends ResponseEnvelope {

    public InternalServerErrorResponse() {
	super(new EmptyResponse(500));
    }

}
