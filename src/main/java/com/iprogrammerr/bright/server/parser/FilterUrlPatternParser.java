package com.iprogrammerr.bright.server.parser;

public interface FilterUrlPatternParser {

    boolean isPrimary(String urlPattern);

    boolean match(String url, String urlPattern);
}
