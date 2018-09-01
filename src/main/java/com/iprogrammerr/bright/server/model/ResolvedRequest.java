package com.iprogrammerr.bright.server.model;

import java.util.List;

public class ResolvedRequest {

    private List<Header> headers;
    private Pairs parameters;
    private Pairs pathVariables;
    private byte[] body;

    public ResolvedRequest(Request request, Pairs parameters, Pairs pathVariables) {
	this(request.getHeaders(), parameters, pathVariables, request.getBody());
    }

    public ResolvedRequest(List<Header> headers, Pairs parameters, Pairs pathVariables, byte[] body) {
	this.headers = headers;
	this.parameters = parameters;
	this.pathVariables = pathVariables;
	this.body = body;
    }

    public List<Header> getHeaders() {
	return headers;
    }

    public byte[] getBody() {
	return body;
    }

    public <T> boolean hasParameter(String key, Class<T> clazz) {
	return parameters.has(key, clazz);
    }

    public <T> T getParameter(String key, Class<T> clazz) {
	return parameters.get(key, clazz);
    }

    public <T> boolean hasPathVariable(String key, Class<T> clazz) {
	return pathVariables.has(key, clazz);
    }

    public <T> T getPathVariable(String key, Class<T> clazz) {
	return pathVariables.get(key, clazz);
    }

}
