package com.iprogrammerr.bright.server.response;

import java.util.Collections;
import java.util.List;

import com.iprogrammerr.bright.server.constants.HeaderKey;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.model.Header;

public class ContentResponse implements Response {

    private int responseCode;
    private byte[] body;
    private Header contentTypeHeader;

    public ContentResponse(int responseCode, String contentType, byte[] body) {
	this.responseCode = responseCode;
	this.body = body;
	this.contentTypeHeader = new Header(HeaderKey.CONTENT_TYPE, contentType);
    }
    
    public ContentResponse(ResponseCode responseCode, String contentType, byte[] body) {
	this(responseCode.getValue(), contentType, body);
    }

    @Override
    public int getResponseCode() {
	return responseCode;
    }

    @Override
    public List<Header> getHeaders() {
	return Collections.singletonList(contentTypeHeader);
    }

    @Override
    public boolean hasBody() {
	return true;
    }

    @Override
    public byte[] getBody() {
	return body;
    }

}
