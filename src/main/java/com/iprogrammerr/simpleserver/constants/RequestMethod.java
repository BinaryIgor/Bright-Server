package com.iprogrammerr.simpleserver.constants;

public enum RequestMethod {

    GET, POST, PUT, DELETE;

    public static RequestMethod createFromString(String method) {
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
	return null;
    }
}
