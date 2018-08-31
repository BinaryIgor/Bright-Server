package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.constants.HeaderValue;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.model.Header;

public class PlainTextResponse implements Response {

    private ContentResponse contentResponse;

    public PlainTextResponse(int responseCode, String text) {
  	this.contentResponse = new ContentResponse(responseCode, HeaderValue.TEXT_PLAIN.getValue(), text.getBytes());
      }
    
    public PlainTextResponse(ResponseCode responseCode, String text) {
	this(responseCode.getValue(), text);
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
