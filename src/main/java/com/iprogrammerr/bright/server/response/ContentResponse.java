package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.ContentLengthHeader;
import com.iprogrammerr.bright.server.header.Header;

public class ContentResponse implements Response {

    private int responseCode;
    private byte[] body;
    private List<Header> headers;

   
    public ContentResponse(int responseCode, Header contentTypeHeader, byte[] body) {
	this(responseCode, contentTypeHeader, body, new ArrayList<>());
    }
    
    public ContentResponse(int responseCode, Header contentTypeHeader, byte[] body, Header...headers) {
	this(responseCode, contentTypeHeader, body, Arrays.asList(headers));
    }
    
    public ContentResponse(int responseCode, Header contentTypeHeader, byte[] body, List<Header> headers) {
	this.responseCode = responseCode;
	this.body = body;
	this.headers = headers;
	this.headers.add(contentTypeHeader);
	this.headers.add(new ContentLengthHeader(body.length));
    }
    
    
    @Override
    public int responseCode() {
	return responseCode;
    }

    @Override
    public List<Header> headers() {
	return headers;
    }

    @Override
    public boolean hasBody() {
	return true;
    }

    @Override
    public byte[] body() {
	return body;
    }

}
