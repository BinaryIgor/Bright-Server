package com.iprogrammerr.bright.server.pattern;

public interface FileUrlPattern {

    boolean match(String url);

    String filePath(String url);
}
