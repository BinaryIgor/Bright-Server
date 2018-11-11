package com.iprogrammerr.bright.server.pattern;

public interface Type {

	<T> Class<T> type(String type);

	<T> T value(Class<T> type, String value) throws Exception;

	Object probedValue(String value);
}
