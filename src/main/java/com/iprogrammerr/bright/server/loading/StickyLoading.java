package com.iprogrammerr.bright.server.loading;

public class StickyLoading<T> implements Loading<T> {

    private Loading<T> origin;
    private T value;

    public StickyLoading(Loading<T> origin) {
	this.origin = origin;
    }

    @Override
    public T load() {
	if (value == null) {
	    value = origin.load();
	}
	return value;
    }

}
