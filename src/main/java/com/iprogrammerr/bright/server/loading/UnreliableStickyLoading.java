package com.iprogrammerr.bright.server.loading;

public class UnreliableStickyLoading<T> implements UnreliableLoading<T> {

    private final UnreliableLoading<T> base;
    private T value;

    public UnreliableStickyLoading(UnreliableLoading<T> base) {
	this.base = base;
    }

    @Override
    public T load() throws Exception {
	if (value == null) {
	    value = base.load();
	}
	return value;
    }

}
