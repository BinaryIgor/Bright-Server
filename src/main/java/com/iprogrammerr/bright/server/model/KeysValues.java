package com.iprogrammerr.bright.server.model;

public interface KeysValues {

    public <T> boolean has(String key, Class<T> clazz);

    public <T> T value(String key, Class<T> clazz) throws Exception;

}
