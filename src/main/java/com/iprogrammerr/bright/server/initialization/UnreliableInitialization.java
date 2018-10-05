package com.iprogrammerr.bright.server.initialization;

public interface UnreliableInitialization<T> {
    T value() throws Exception;
}
