package com.iprogrammerr.bright.server.loading;

public class StickyLoading<T> implements Loading<T> {

    private final Loading<T> base;
    private T value;

    public StickyLoading(Loading<T> base) {
	this.base = base;
    }

    @Override
    public T load() {
	if (value == null) {
	    value = base.load();
	}
	return value;
    }

}
