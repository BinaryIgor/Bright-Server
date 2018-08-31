package com.iprogrammerr.bright.server.model;

import java.util.List;

import com.iprogrammerr.bright.server.constants.HeaderKey;
import com.iprogrammerr.bright.server.exception.ObjectNotFoundException;

public class Request {

    private String method;
    private String path;
    private List<Header> headers;
    private byte[] body;

    public Request(String method, String path, List<Header> headers, byte[] body) {
	this.method = method;
	this.path = path;
	this.headers = headers;
	this.body = body;
    }

    public String getMethod() {
	return method;
    }

    public String getPath() {
	return path;
    }

    public List<Header> getHeaders() {
	return headers;
    }

    public byte[] getBody() {
	return body;
    }

    public boolean hasHeader(HeaderKey headerKey) {
	return hasHeader(headerKey.getValue());
    }

    public boolean hasHeader(String headerKey) {
	for (Header header : headers) {
	    if (header.getKey().equals(headerKey)) {
		return true;
	    }
	}
	return false;
    }

    public String getHeader(HeaderKey key) {
	return getHeader(key.getValue());
    }

    public String getHeader(String key) {
	for (Header header : headers) {
	    if (header.getKey().equals(key)) {
		return header.getValue();
	    }
	}
	throw new ObjectNotFoundException();
    }

    public void removeContextFromPath(String contextPath) {
	if (path.startsWith(contextPath)) {
	    path = path.replace(contextPath + "/", "");
	}
    }

}
