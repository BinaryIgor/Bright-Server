package com.iprogrammerr.simple.http.server.exception;

public class RequestException extends RuntimeException {

    public RequestException() {

    }

    private RequestException(String message) {
	super(message);
    }

    public static RequestException createLackOfMethodException() {
	return new RequestException("Lack of method type");
    }
}
