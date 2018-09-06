package com.iprogrammerr.bright.server.header;

public class HttpHeader implements Header {

    private String key;
    private String value;

    public HttpHeader(String key, String value) {
	this.key = key;
	this.value = value;
    }

    @Override
    public String key() {
	return key;
    }

    @Override
    public String value() {
	return value;
    }

    @Override
    public boolean is(String key) {
	return this.key.equalsIgnoreCase(key);
    }
    
    @Override
    public String writable() {
	return key + ": " + value;
    }

}
