package com.iprogrammerr.bright.server.header;

public interface Header {

    String key();
    String value();
    boolean is(String key);
}
