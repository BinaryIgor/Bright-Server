package com.iprogrammerr.bright.server.model;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.exception.ObjectNotFoundException;

public class StringsObjects implements KeysValues {

    private List<KeyValue> keysValues;

    public StringsObjects() {
	this.keysValues = new ArrayList<>();
    }

    public StringsObjects(List<KeyValue> keyValues) {
	this.keysValues = keyValues;
    }

    @Override
    public <T> boolean has(String key, Class<T> clazz) {
	for (KeyValue keyValue : keysValues) {
	    if (keyValue.key().equals(key) && keyValue.value().getClass().isAssignableFrom(clazz)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public <T> T value(String key, Class<T> clazz) throws Exception {
	for (KeyValue keyValue : keysValues) {
	    if (keyValue.key().equals(key) && keyValue.value().getClass().isAssignableFrom(clazz)) {
		return (T) keyValue.value();
	    }
	}
	throw new ObjectNotFoundException();
    }
}
