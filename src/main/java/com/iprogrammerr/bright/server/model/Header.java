package com.iprogrammerr.bright.server.model;

import com.iprogrammerr.bright.server.constants.HeaderKey;
import com.iprogrammerr.bright.server.constants.HeaderValue;

public class Header {

    private String key;
    private String value;

    public Header(String key, String value) {
	this.key = key;
	this.value = value;
    }

    public Header(HeaderKey key, String value) {
	this(key.getValue(), value);
    }


    public Header(HeaderKey key, HeaderValue value) {
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
