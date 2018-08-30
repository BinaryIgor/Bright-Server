package com.iprogrammerr.bright.server.model;

import com.iprogrammerr.bright.server.constants.HeaderValue;
import com.iprogrammerr.bright.server.constants.RequestHeaderKey;
import com.iprogrammerr.bright.server.constants.ResponseHeaderKey;

public class Header {

    private String key;
    private String value;

    public Header(String key, String value) {
	this.key = key;
	this.value = value;
    }

    public Header(RequestHeaderKey key, String value) {
	this(key.getValue(), value);
    }

    public Header(ResponseHeaderKey key, String value) {
	this(key.getValue(), value);
    }

    public Header(RequestHeaderKey key, HeaderValue value) {
	this(key.getValue(), value.getValue());
    }

    public Header(ResponseHeaderKey key, HeaderValue value) {
	this(key.getValue(), value.getValue());
    }

    public Header(String key, HeaderValue value) {
	this(key, value.getValue());
    }

    public String getKey() {
	return key;
    }

    public String getValue() {
	return value;
    }

    @Override
    public String toString() {
	return key + ": " + value;
    }

}
