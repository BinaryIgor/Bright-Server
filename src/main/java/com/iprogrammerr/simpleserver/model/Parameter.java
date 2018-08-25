package com.iprogrammerr.simpleserver.model;

public class Parameter extends Pair {

    public Parameter(String key, Object value) {
	super(key, value);
    }

    @Override
    public String toString() {
	return "Parameter [key=" + key + ", value=" + value + "," + value.getClass().getName() + "]";
    }

}
