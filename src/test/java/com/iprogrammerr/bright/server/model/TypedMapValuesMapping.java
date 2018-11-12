package com.iprogrammerr.bright.server.model;

import com.iprogrammerr.bright.server.test.DynamicMapping;

public final class TypedMapValuesMapping implements DynamicMapping<TypedMap> {

	private final String key;

	public TypedMapValuesMapping(String key) {
		this.key = key;
	}

	@Override
	public <To> To value(TypedMap from, Class<To> clazz) throws Exception {
		To value;
		if (clazz.isAssignableFrom(Boolean.class)) {
			value = clazz.cast(from.booleanValue(this.key));
		} else if (clazz.isAssignableFrom(Integer.class)) {
			value = clazz.cast(from.intValue(this.key));
		} else if (clazz.isAssignableFrom(Long.class)) {
			value = clazz.cast(from.longValue(this.key));
		} else if (clazz.isAssignableFrom(Float.class)) {
			value = clazz.cast(from.floatValue(this.key));
		} else if (clazz.isAssignableFrom(Double.class)) {
			value = clazz.cast(from.doubleValue(this.key));
		} else if (clazz.isAssignableFrom(byte[].class)) {
			value = clazz.cast(from.binaryValue(this.key));
		} else {
			value = clazz.cast(from.stringValue(this.key));
		}
		return value;
	}
}
