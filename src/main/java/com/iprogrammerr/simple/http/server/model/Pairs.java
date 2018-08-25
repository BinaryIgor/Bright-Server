package com.iprogrammerr.simple.http.server.model;

import java.util.List;

import com.iprogrammerr.simple.http.server.exception.ObjectNotFoundException;

public abstract class Pairs<T extends Pair> {

    protected List<T> pairs;

    public Pairs(List<T> pairs) {
	this.pairs = pairs;
    }

    public boolean has(String key, Class clazz) {
	for (Pair pair : pairs) {
	    if (pair.getKey().equals(key) && pair.getValue().getClass().isAssignableFrom(clazz)) {
		return true;
	    }
	}
	return false;
    }

    public <T> T get(String key, Class<T> clazz) {
	for (Pair pair : pairs) {
	    if (pair.getKey().equals(key) && pair.getValue().getClass().isAssignableFrom(clazz)) {
		return (T) pair.getValue();
	    }
	}
	throw new ObjectNotFoundException();
    }

    public void set(List<T> pairs) {
	this.pairs = pairs;
    }

}
