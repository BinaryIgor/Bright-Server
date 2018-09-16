package com.iprogrammerr.bright.server.pattern;

public interface ToFilterUrlPattern {

    boolean primary();

    boolean match(String url);
}
