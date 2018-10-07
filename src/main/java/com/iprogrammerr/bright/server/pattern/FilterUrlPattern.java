package com.iprogrammerr.bright.server.pattern;

public interface FilterUrlPattern {

    boolean isPrimary();

    boolean isMatched(String url);
}
