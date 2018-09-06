package com.iprogrammerr.bright.server.header;

public abstract class HeaderEnvelope implements Header{

    private Header header;
    
    public HeaderEnvelope(Header header) {
	this.header = header;
    }

    @Override
    public String key() {
	return header.key();
    }

    @Override
    public String value() {
	return header.value();
    }

    @Override
    public boolean is(String key) {
	return header.is(key);
    }
    
    @Override
    public String writable() {
	return header.writable();
    }

}
