package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.constants.HeaderValue;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.model.Header;

public class JsonResponse implements Response {

    private ContentResponse contentResponse;

    public JsonResponse(ResponseCode responseCode, String json) {
	this.contentResponse = new ContentResponse(responseCode, HeaderValue.JSON.getValue(), json.getBytes());

    }

    @Override
    public ResponseCode getResponseCode() {
	return contentResponse.getResponseCode();
    }

    @Override
    public List<Header> getHeaders() {
	return contentResponse.getHeaders();
    }

    @Override
    public boolean hasBody() {
	return true;
    }

    @Override
    public byte[] getBody() {
	return contentResponse.getBody();
    }

    @Override
    public Header getContentTypeHeader() {
	return contentResponse.getContentTypeHeader();
    }

}