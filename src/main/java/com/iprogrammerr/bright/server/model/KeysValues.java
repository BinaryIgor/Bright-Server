package com.iprogrammerr.bright.server.model;

import java.util.List;

public interface KeysValues {

    <T> boolean has(String key, Class<T> clazz);

    <T> T value(String key, Class<T> clazz) throws Exception;

    KeysValues add(String key, Object value);

    List<KeyValue> keysValues();

    boolean empty();

}
