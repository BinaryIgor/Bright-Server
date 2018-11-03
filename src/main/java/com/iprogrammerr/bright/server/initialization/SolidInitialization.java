package com.iprogrammerr.bright.server.initialization;

public final class SolidInitialization<T> implements Initialization<T> {

	private final Initialization<T> base;
	private T value;

	public SolidInitialization(Initialization<T> base) {
		this.base = base;
	}

	@Override
	public T value() {
		synchronized (this) {
			if (this.value == null) {
				this.value = this.base.value();
			}
			return this.value;
		}
	}
}
