package com.iprogrammerr.simple.http.server.exception;

import com.iprogrammerr.simple.http.server.constants.ResponseCode;

public abstract class HttpException extends RuntimeException {

    private ResponseCode responseCode;

    public HttpException(ResponseCode responseCode) {
	this.responseCode = responseCode;
    }

    public HttpException(ResponseCode responseCode, String message) {
	super(message);
	this.responseCode = responseCode;
    }

    public HttpException(ResponseCode responseCode, Exception exception) {
	super(exception);
	this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
	return responseCode;
    }

}
