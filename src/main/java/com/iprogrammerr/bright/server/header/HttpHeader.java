package com.iprogrammerr.bright.server.header;

public final class HttpHeader implements Header {

    private final String key;
    private final String value;

    public HttpHeader(String key, String value) {
	this.key = key;
	this.value = value;
    }

    @Override
    public String key() {
	return this.key;
    }

    @Override
    public String value() {
	return this.value;
    }

    @Override
    public boolean is(String key) {
	return this.key.equalsIgnoreCase(key);
    }

    @Override
    public String writable() {
	return this.key + ": " + this.value;
    }

    @Override
    public String toString() {
	return writable();
    }

}
