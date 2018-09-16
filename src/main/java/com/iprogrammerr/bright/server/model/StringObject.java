package com.iprogrammerr.bright.server.model;

public class StringObject implements KeyValue {

    private final String key;
    private final Object value;

    public StringObject(String key, Object value) {
	this.key = key;
	this.value = value;
    }

    @Override
    public String key() {
	return key;
    }

    @Override
    public Object value() {
	return value;
    }

}
