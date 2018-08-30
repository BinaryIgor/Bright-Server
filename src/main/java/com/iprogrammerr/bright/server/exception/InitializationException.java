package com.iprogrammerr.bright.server.exception;

public class InitializationException extends RuntimeException {

    public InitializationException(Exception exception) {
	super(exception);
    }

}
