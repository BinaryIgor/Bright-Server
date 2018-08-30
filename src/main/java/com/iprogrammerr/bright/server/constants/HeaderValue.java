package com.iprogrammerr.bright.server.constants;

public enum HeaderValue {

    TEXT_PLAIN("text/plain"), JSON("Application/json");

    private String value;

    private HeaderValue(String value) {
	this.value = value;
    }

    public boolean equalsByValue(String value) {
	return this.value.equals(value);
    }

    public String getValue() {
	return value;
    }

}
