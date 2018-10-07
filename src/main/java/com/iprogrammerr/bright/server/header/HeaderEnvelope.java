package com.iprogrammerr.bright.server.header;

public abstract class HeaderEnvelope implements Header {

    private final Header base;

    public HeaderEnvelope(Header base) {
	this.base = base;
    }

    @Override
    public final String key() {
	return this.base.key();
    }

    @Override
    public final String value() {
	return this.base.value();
    }

    @Override
    public final boolean is(String key) {
	return this.base.is(key);
    }

    @Override
    public final String writable() {
	return this.base.writable();
    }

}
