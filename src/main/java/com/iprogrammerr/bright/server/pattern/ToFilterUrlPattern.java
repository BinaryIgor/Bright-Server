package com.iprogrammerr.bright.server.pattern;

public interface ToFilterUrlPattern {

    boolean isPrimary();

    boolean isMatched(String url);
}
