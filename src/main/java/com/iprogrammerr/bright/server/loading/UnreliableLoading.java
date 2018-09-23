package com.iprogrammerr.bright.server.loading;

public interface UnreliableLoading<T> {
    T load() throws Exception;
}
