package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.constants.HeaderValue;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.model.Header;

public class JsonResponse implements Response {

    private ContentResponse contentResponse;

    public JsonResponse(int responseCode, String json) {
	this.contentResponse = new ContentResponse(responseCode, HeaderValue.JSON.getValue(), json.getBytes());

    }
    
    public JsonResponse(ResponseCode responseCode, String json) {
	this(responseCode.getValue(), json);
    }
    

    @Override
    public int getResponseCode() {
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

}
