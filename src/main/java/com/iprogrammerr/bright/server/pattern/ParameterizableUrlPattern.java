package com.iprogrammerr.bright.server.pattern;

import com.iprogrammerr.bright.server.model.Primitives;

public interface ParameterizableUrlPattern extends UrlPattern {

	Primitives pathVariables(String url);

	Primitives parameters(String url);

	boolean hasParameters();

	boolean hasPathVariables();
}
