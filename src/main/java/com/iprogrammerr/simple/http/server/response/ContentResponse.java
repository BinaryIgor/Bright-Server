package com.iprogrammerr.simple.http.server.response;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.constants.ResponseHeaderKey;
import com.iprogrammerr.simple.http.server.model.Header;

public class ContentResponse implements Response {

    private ResponseCode responseCode;
    private byte[] body;
    private Header contentTypeHeader;

    public ContentResponse(ResponseCode responseCode, String contentType, byte[] body) {
	this.responseCode = responseCode;
	this.body = body;
	this.contentTypeHeader = new Header(ResponseHeaderKey.CONTENT_TYPE, contentType);
    }

    @Override
    public ResponseCode getResponseCode() {
	return responseCode;
    }

    @Override
    public List<Header> getHeaders() {
	List<Header> headers = new ArrayList<>();
	headers.add(contentTypeHeader);
	return headers;
    }

    @Override
    public boolean hasBody() {
	return true;
    }

    @Override
    public byte[] getBody() {
	return body;
    }

    @Override
    public Header getContentTypeHeader() {
	return contentTypeHeader;
    }

}
