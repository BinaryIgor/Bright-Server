package com.iprogrammerr.bright.server.initialization;

public final class StickyInitialization<T> implements Initialization<T> {

    private final Initialization<T> base;
    private T value;
    private boolean unstick;

    public StickyInitialization(Initialization<T> base) {
	this.base = base;
    }

    @Override
    public T value() {
	if (this.value == null || this.unstick) {
	    this.value = this.base.value();
	    this.unstick = false;
	}
	return this.value;
    }

    public void unstick() {
	this.unstick = true;
    }

}
