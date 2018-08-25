package com.iprogrammerr.simple.http.server.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.RequestHeaderKey;
import com.iprogrammerr.simple.http.server.exception.ObjectNotFoundException;

public class Request {

    private String method;
    private String path;
    private List<Header> headers;
    private List<Parameter> parameters;
    private List<PathVariable> pathVariables;
    private byte[] body;

    public Request(String method, String path, List<Header> headers, List<Parameter> parameters, byte[] body) {
	this.method = method;
	this.path = path;
	this.headers = headers;
	this.parameters = parameters;
	this.pathVariables = new ArrayList<>();
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

    public boolean hasHeader(RequestHeaderKey headerKey) {
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

    public String getHeader(RequestHeaderKey key) {
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

    public boolean hasParameter(String key, Class clazz) {
	for (Parameter parameter : parameters) {
	    if (parameter.getKey().equals(key) && parameter.getValue().getClass().isAssignableFrom(clazz)) {
		return true;
	    }
	}
	return false;
    }

    public <T> T getParameter(String key, Class<T> clazz) {
	for (Parameter parameter : parameters) {
	    if (parameter.getKey().equals(key) && parameter.getValue().getClass().isAssignableFrom(clazz)) {
		return (T) parameter.getValue();
	    }
	}
	throw new ObjectNotFoundException();
    }

    public <T> T getPathVariable(String key, Class<T> clazz) {
	for (PathVariable pathVariable : pathVariables) {
	    if (pathVariable.getKey().equals(key) && pathVariable.getValue().getClass().isAssignableFrom(clazz)) {
		return (T) pathVariable.getValue();
	    }
	}
	throw new ObjectNotFoundException();
    }

    public List<PathVariable> getPathVariables() {
	return pathVariables;
    }

    public void setPathVariables(List<PathVariable> pathVariables) {
	this.pathVariables = pathVariables;
    }

    // TODO - should it be here?
    public void removeContextFromPath(String contextPath) {
	if (path.startsWith(contextPath)) {
	    path = path.replace(contextPath + "/", "");
	}
    }

    @Override
    public String toString() {
	return "Request [method=" + method + ", path=" + path + ", headers=" + headers + ", parameters=" + parameters
		+ ", body=" + Arrays.toString(body) + "]";
    }

}
