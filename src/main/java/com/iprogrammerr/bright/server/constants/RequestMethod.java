package com.iprogrammerr.bright.server.constants;

public enum RequestMethod {

    GET("GET"), HEAD("HEAD"), POST("POST"), PUT("PUT"), DELETE("DELETE"), CONNECT("CONNECT"), OPTIONS("OPTIONS"),
    TRACE("TRACE"), PATCH("PATCH");

    private String value;

    private RequestMethod(String value) {
	this.value = value;
    }

    public boolean equalsByValue(String requestMethod) {
	return value.equals(requestMethod.toUpperCase());
    }

    public String getValue() {
        return value;
    }
    
}
