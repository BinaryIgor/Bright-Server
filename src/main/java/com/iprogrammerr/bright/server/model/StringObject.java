package com.iprogrammerr.bright.server.model;

public class StringObject implements KeyValue {

    private String key;
    private Object value;

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
