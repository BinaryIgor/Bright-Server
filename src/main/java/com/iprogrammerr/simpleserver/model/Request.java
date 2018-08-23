package com.iprogrammerr.simpleserver.model;

import java.util.List;

public class Request {

    private String method;
    private String path;
    private List<Header> headers;
    private String body;

    public Request(String method, String path, List<Header> headers, String body) {
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

    public String getBody() {
	return body;
    }

    @Override
    public String toString() {
	return "Request [method=" + method + ", path=" + path + ", headers=" + headers + ", body=" + body + "]";
    }

}
