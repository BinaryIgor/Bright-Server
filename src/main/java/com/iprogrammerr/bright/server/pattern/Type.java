package com.iprogrammerr.bright.server.pattern;

public interface Type {

    <T> Class<T> type(String type);

    Object value(String type, String value) throws Exception;
}
