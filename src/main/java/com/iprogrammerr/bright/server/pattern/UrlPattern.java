package com.iprogrammerr.bright.server.pattern;

import com.iprogrammerr.bright.server.model.KeysValues;

public interface UrlPattern {

    boolean isMatched(String url);

    KeysValues pathVariables(String url);

    KeysValues parameters(String url);

    boolean hasParameters();

    boolean hasPathVariables();
}
