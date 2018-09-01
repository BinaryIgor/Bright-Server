package com.iprogrammerr.bright.server.model;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.exception.ObjectNotFoundException;

public class Pairs {

    private List<Pair> pairs;

    public Pairs() {
	this.pairs = new ArrayList<>();
    }

    public Pairs(List<Pair> pairs) {
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

    public void add(Pair pair) {
	if (!this.pairs.contains(pair)) {
	    pairs.add(pair);
	}
    }

    public void add(List<Pair> pairs) {
	if (pairs == null) {
	    return;
	}
	for (Pair pair : pairs) {
	    if (!this.pairs.contains(pair)) {
		pairs.add(pair);
	    }
	}
    }

    public void add(Pairs pairs) {
	this.pairs.addAll(pairs.pairs);
    }
}
