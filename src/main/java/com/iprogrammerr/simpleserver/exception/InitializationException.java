package com.iprogrammerr.simpleserver.exception;

public class InitializationException extends RuntimeException {

    public InitializationException(Exception exception) {
	super(exception);
    }

}
