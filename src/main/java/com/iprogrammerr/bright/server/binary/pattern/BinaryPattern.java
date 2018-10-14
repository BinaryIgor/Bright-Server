package com.iprogrammerr.bright.server.binary.pattern;

public interface BinaryPattern {

    byte[] value();

    int index(byte[] content);
}
