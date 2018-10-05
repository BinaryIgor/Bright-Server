package com.iprogrammerr.bright.server.pattern;

public interface FileUrlPattern {

    boolean isMatched(String url);

    String filePath(String url);
}
