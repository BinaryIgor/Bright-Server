package com.iprogrammerr.simpleserver.model;

public abstract class Pair {

    protected String key;
    protected Object value;

    public Pair(String key, Object value) {
	this.key = key;
	this.value = value;
    }

    public String getKey() {
	return key;
    }

    public Object getValue() {
	return value;
    }

}
