package com.iprogrammerr.simple.http.server.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.ResponseCode;

public class Response {

    private Header contentTypeHeader;
    private Header contentLengthHeader;
    private final List<Header> headers;
    private byte[] body;
    private ResponseCode code = ResponseCode.NOT_FOUND;

    public Response() {
	contentTypeHeader = Header.createTextContentType();
	headers = new ArrayList<>();
    }

    public List<Header> getHeaders() {
	return headers;
    }

    public void setBody(byte[] body) {
	this.body = body;
	if (body != null) {
	    contentLengthHeader = Header.createContentLengthHeader(body.length);
	}
    }

    public byte[] getBody() {
	return body;
    }

    public ResponseCode getCode() {
	return code;
    }

    public void addHeader(Header header) {
	headers.add(header);
    }

    public void setCode(ResponseCode code) {
	this.code = code;
    }

    public Header getContentTypeHeader() {
	return contentTypeHeader;
    }

    public void setContentTypeHeader(Header contentTypeHeader) {
	this.contentTypeHeader = contentTypeHeader;
    }

    public Header getContentLengthHeader() {
	return contentLengthHeader;
    }

    public boolean hasBody() {
	return body != null && body.length > 0;
    }

    @Override
    public String toString() {
	return "Response [contentTypeHeader=" + contentTypeHeader + ", contentLengthHeader=" + contentLengthHeader
		+ ", headers=" + headers + ", body=" + Arrays.toString(body) + ", code=" + code + "]";
    }

}
