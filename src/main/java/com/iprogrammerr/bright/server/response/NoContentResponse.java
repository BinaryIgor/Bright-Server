package com.iprogrammerr.bright.server.response;

public class NoContentResponse extends ResponseEnvelope {

    public NoContentResponse() {
	super(new EmptyResponse(204));
    }

}
