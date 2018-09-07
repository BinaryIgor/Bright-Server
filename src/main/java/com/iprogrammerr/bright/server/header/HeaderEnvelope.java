package com.iprogrammerr.bright.server.header;

public abstract class HeaderEnvelope implements Header {

    private Header header;

    public HeaderEnvelope(Header header) {
	this.header = header;
    }

    @Override
    public final String key() {
	return header.key();
    }

    @Override
    public final String value() {
	return header.value();
    }

    @Override
    public final boolean is(String key) {
	return header.is(key);
    }

    @Override
    public final String writable() {
	return header.writable();
    }

}
