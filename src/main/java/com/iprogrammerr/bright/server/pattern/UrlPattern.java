package com.iprogrammerr.bright.server.pattern;

import com.iprogrammerr.bright.server.model.TypedMap;

public interface UrlPattern {

    boolean isMatched(String url);

    TypedMap pathVariables(String url);

    TypedMap parameters(String url);

    boolean hasParameters();

    boolean hasPathVariables();
}
