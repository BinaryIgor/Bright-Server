package com.iprogrammerr.simple.http.server.exception;

public class InitializationException extends RuntimeException {

    public InitializationException(Exception exception) {
	super(exception);
    }

}
