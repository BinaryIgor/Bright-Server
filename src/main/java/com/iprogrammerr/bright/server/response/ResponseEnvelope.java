package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.HttpHeader;

public abstract class ResponseEnvelope implements Response {

    private Response response;
    
    public ResponseEnvelope(Response response) {
	this.response = response;
    }
    
    @Override
    public int responseCode() {
	return response.responseCode();
    }

    @Override
    public List<HttpHeader> headers() {
	return response.headers();
    }

    @Override
    public boolean hadBody() {
	return response.hadBody();
    }

    @Override
    public byte[] body() {
	return response.body();
    }

}
