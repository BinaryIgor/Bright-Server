package com.iprogrammerr.bright.server.pattern;

public interface Type {

    <T> Class<T> type(String type);

    <T> Object value(Class<T> type, String value) throws Exception;
}
