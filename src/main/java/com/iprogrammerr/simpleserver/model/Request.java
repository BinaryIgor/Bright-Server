package com.iprogrammerr.simpleserver.model;

import java.util.List;

public class Request {

    private String method;
    private String path;
    private List<Header> headers;
    private List<Parameter> parameters;
    private List<Number> pathVariables;
    private byte[] body;

    public Request(String method, String path, List<Header> headers, List<Parameter> parameters,
	    List<Number> pathVariables, byte[] body) {
	this.method = method;
	this.path = path;
	this.headers = headers;
	this.parameters = parameters;
	this.pathVariables = pathVariables;
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

    public List<Parameter> getParameters() {
	return parameters;
    }

    public List<Number> getPathVariables() {
	return pathVariables;
    }

    @Override
    public String toString() {
	return "Request [method=" + method + ", path=" + path + ", headers=" + headers + ", body=" + body + "]";
    }

}
