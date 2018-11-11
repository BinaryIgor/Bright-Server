package com.iprogrammerr.bright.server.pattern;

import com.iprogrammerr.bright.server.model.TypedMap;

public interface ParameterizableUrlPattern extends UrlPattern {

	TypedMap pathVariables(String url);

	TypedMap parameters(String url);

	boolean hasParameters();

	boolean hasPathVariables();
}
