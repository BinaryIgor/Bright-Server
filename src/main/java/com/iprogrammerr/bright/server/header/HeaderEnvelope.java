package com.iprogrammerr.bright.server.header;

public abstract class HeaderEnvelope implements Header {

    private Header base;

    public HeaderEnvelope(Header base) {
	this.base = base;
    }

    @Override
    public final String key() {
	return base.key();
    }

    @Override
    public final String value() {
	return base.value();
    }

    @Override
    public final boolean is(String key) {
	return base.is(key);
    }

    @Override
    public final String writable() {
	return base.writable();
    }

}
