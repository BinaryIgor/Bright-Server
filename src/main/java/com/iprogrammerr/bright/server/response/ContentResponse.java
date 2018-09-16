package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.ContentLengthHeader;
import com.iprogrammerr.bright.server.header.ContentTypeHeader;
import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public class ContentResponse implements Response {

    private int responseCode;
    private byte[] body;
    private List<Header> headers;

    public ContentResponse(int responseCode, ResponseBody body) {
	this(responseCode, body.contentType(), body.content(), new ArrayList<>());
    }

    public ContentResponse(int responseCode, ResponseBody body, Header... headers) {
	this(responseCode, body.contentType(), body.content(), new ArrayList<>(Arrays.asList(headers)));
    }

    public ContentResponse(int responseCode, ResponseBody body, List<Header> headers) {
	this(responseCode, body.contentType(), body.content(), headers);
    }

    public ContentResponse(int responseCode, String textBody) {
	this(responseCode, "text/plain", textBody.getBytes(), new ArrayList<>());
    }

    public ContentResponse(int responseCode, String contentType, byte[] body, List<Header> headers) {
	this.responseCode = responseCode;
	this.body = body;
	this.headers = headers;
	this.headers.add(new ContentTypeHeader(contentType));
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
