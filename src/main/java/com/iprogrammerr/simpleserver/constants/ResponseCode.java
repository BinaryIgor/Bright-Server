package com.iprogrammerr.simpleserver.constants;

public enum ResponseCode {

    OK(200), CREATED(201), ACCEPTED(202), NO_CONTENT(204), BAD_REQUEST(400), UNAUTHORISED(401), FORBIDDEN(
	    403), NOT_FOUND(404), METHOD_NOT_ALLOWED(
		    405), CONFLICT(409), UNSUPPORTED_MEDIA_TYPE(415), INTERNAL_SERVER_ERROR(500), GATEWAY_TIMEOUT(504);

    private int value;

    ResponseCode(int value) {
	this.value = value;
    }

    public int getValue() {
	return value;
    }
}
