package com.iprogrammerr.bright.server.model;

import java.util.ArrayList;
import java.util.List;

public final class Attributes implements TypedMap {

    private final List<StringObject> values;

    private Attributes(List<StringObject> values) {
	this.values = values;
    }

    public Attributes() {
	this(new ArrayList<>(10));
    }

    @Override
    public TypedMap put(String key, Object value) {
	int index = keyIndex(key);
	if (index >= 0) {
	    this.values.set(index, new StringObject(key, value));
	} else {
	    this.values.add(new StringObject(key, value));
	}
	return this;
    }

    private int keyIndex(String key) {
	int index = -1;
	for (int i = 0; i < this.values.size(); i++) {
	    if (this.values.get(i).key().equals(key)) {
		index = i;
		break;
	    }
	}
	return index;
    }

    @Override
    public boolean booleanValue(String key) throws Exception {
	return value(key, Boolean.class);
    }

    @Override
    public int intValue(String key) throws Exception {
	return value(key, Integer.class);
    }

    @Override
    public long longValue(String key) throws Exception {
	return value(key, Long.class);
    }

    @Override
    public String stringValue(String key) throws Exception {
	return value(key, String.class);
    }

    @Override
    public float floatValue(String key) throws Exception {
	return value(key, Float.class);
    }

    @Override
    public double doubleValue(String key) throws Exception {
	return value(key, Double.class);
    }

    @Override
    public byte[] binaryValue(String key) throws Exception {
	return value(key, byte[].class);
    }

    @Override
    public List<StringObject> keyValues() {
	return this.values;
    }

    private <T> T value(String key, Class<T> clazz) throws Exception {
	for (KeyValue kv : this.values) {
	    if (kv.key().equals(key) && kv.value().getClass().isAssignableFrom(clazz)) {
		return clazz.cast(kv.value());
	    }
	}
	throw new Exception(String.format("Key %s of type %s is not present", key, clazz));
    }

    @Override
    public boolean isEmpty() {
	return this.values.isEmpty();
    }

    @Override
    public <T> boolean has(String key, Class<T> clazz) {
	int keyIndex = keyIndex(key);
	if (keyIndex >= 0) {
	    return this.values.get(keyIndex).value().getClass().isAssignableFrom(clazz);
	}
	return false;
    }

}
