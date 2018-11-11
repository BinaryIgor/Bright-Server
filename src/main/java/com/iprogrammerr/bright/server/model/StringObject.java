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

	@Override
	public boolean equals(Object object) {
		boolean equal;
		if (object == null || !MutableKeyValue.class.isAssignableFrom(object.getClass())) {
			equal = false;
		} else if (object == this) {
			equal = true;
		} else {
			MutableKeyValue other = (MutableKeyValue) object;
			equal = this.key.equals(other.key()) && this.value.equals(other.value());
		}
		return equal;
	}

	@Override
	public String toString() {
		return "StringObject [key=" + key + ", value=" + value + "]";
	}
}
