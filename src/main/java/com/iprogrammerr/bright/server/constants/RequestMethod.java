package com.iprogrammerr.bright.server.constants;

public enum RequestMethod {

    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE"), OPTIONS("OPTIONS");

    private String value;

    private RequestMethod(String value) {
	this.value = value;
    }

    public boolean equalsByValue(String requestMethod) {
	return value.equals(requestMethod.toUpperCase());
    }

}
