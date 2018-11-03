package com.iprogrammerr.bright.server.model;

public final class StringObject implements MutableKeyValue {

	private final String key;
	private Object value;

	public StringObject(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String key() {
		return this.key;
	}

	@Override
	public Object value() {
		return this.value;
	}

	@Override
	public void revalue(Object value) {
		this.value = value;
	}
}
