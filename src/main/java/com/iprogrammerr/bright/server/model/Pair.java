package com.iprogrammerr.bright.server.model;

import java.util.Objects;

public class Pair {

    private String key;
    private Object value;

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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((key == null) ? 0 : key.hashCode());
	result = prime * result + ((value == null) ? 0 : value.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object object) {
	if (this == object) {
	    return true;
	}
	if (object == null) {
	    return false;
	}
	if (getClass() != object.getClass()) {
	    return false;
	}
	Pair other = (Pair) object;
	if (!Objects.equals(key, other.key)) {
	    return false;
	}
	return Objects.equals(value, other.value);
    }

}
