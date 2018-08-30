package com.iprogrammerr.simple.http.server.exception;

public class RequestException extends RuntimeException {

    public RequestException() {

    }

    private RequestException(String message) {
	super(message);
    }

}
