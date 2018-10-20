package com.iprogrammerr.bright.server.initialization;

public final class UnreliableStickyInitialization<T> implements UnreliableInitialization<T> {

    private final UnreliableInitialization<T> base;
    private T value;
    private boolean unstick;

    public UnreliableStickyInitialization(UnreliableInitialization<T> base) {
	this.base = base;
    }

    @Override
    public T value() throws Exception {
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
