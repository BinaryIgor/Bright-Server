package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public abstract class ResponseEnvelope implements Response {

    private Response response;
    
    public ResponseEnvelope(Response response) {
	this.response = response;
    }
    
    @Override
    public final int responseCode() {
	return response.responseCode();
    }

    @Override
    public final List<Header> headers() {
	return response.headers();
    }

    @Override
    public final boolean hadBody() {
	return response.hadBody();
    }

    @Override
    public final byte[] body() {
	return response.body();
    }

}
