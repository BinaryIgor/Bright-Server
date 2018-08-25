package com.iprogrammerr.simple.http.server.constants;

import com.iprogrammerr.simple.http.server.exception.CreationException;

public enum RequestMethod {

    GET, POST, PUT, DELETE, OPTIONS;

    public static RequestMethod createFromString(String method) {
	method = method.toUpperCase();
	if ("GET".equals(method)) {
	    return GET;
	}
	if ("POST".equals(method)) {
	    return POST;
	}
	if ("PUT".equals(method)) {
	    return PUT;
	}
	if ("DELETE".equals(method)) {
	    return DELETE;
	}
	if ("OPTIONS".equals(method)) {
	    return OPTIONS;
	}
	throw new CreationException(method + " is not a valid request method");
    }
}
