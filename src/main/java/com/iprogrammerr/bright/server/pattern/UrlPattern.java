package com.iprogrammerr.bright.server.pattern;

import com.iprogrammerr.bright.server.model.KeysValues;

public interface UrlPattern {

    boolean match(String url);

    KeysValues readPathVariables(String url);

    KeysValues readParameters(String url);

    boolean hasParameters();

    boolean hasPathVariables();
}
