package com.iprogrammerr.bright.server.pattern;

public interface Type {

    Class type(String type);

    Object value(String type, String value) throws Exception;
}
