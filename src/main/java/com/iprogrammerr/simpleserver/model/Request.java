package com.iprogrammerr.simpleserver.model;

import java.util.List;

public class Request {

    private String method;
    private String path;
    private String resolvedPath;
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

    public String getResolvedPath() {
	return resolvedPath;
    }

    public void setResolvedPath(String resolvedPath) {
	this.resolvedPath = resolvedPath;
    }

    @Override
    public String toString() {
	return "Request [method=" + method + ", path=" + path + ", headers=" + headers + ", body=" + body + "]";
    }

}
