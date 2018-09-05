package com.iprogrammerr.bright.server.response;

import java.util.Collections;
import java.util.List;

import com.iprogrammerr.bright.server.constants.HeaderKey;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.header.HttpHeader;

public class ContentResponse implements Response {

    private int responseCode;
    private byte[] body;
    private HttpHeader contentTypeHeader;

    public ContentResponse(int responseCode, String contentType, byte[] body) {
	this.responseCode = responseCode;
	this.body = body;
	this.contentTypeHeader = new HttpHeader(HeaderKey.CONTENT_TYPE, contentType);
    }
    
    public ContentResponse(ResponseCode responseCode, String contentType, byte[] body) {
	this(responseCode.getValue(), contentType, body);
    }

    @Override
    public int responseCode() {
	return responseCode;
    }

    @Override
    public List<HttpHeader> headers() {
	return Collections.singletonList(contentTypeHeader);
    }

    @Override
    public boolean hadBody() {
	return true;
    }

    @Override
    public byte[] body() {
	return body;
    }

}
