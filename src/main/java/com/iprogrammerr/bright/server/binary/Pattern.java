package com.iprogrammerr.bright.server.binary;

public interface Pattern {

    byte[] value();

    int index(byte[] content);
}
