package com.iprogrammerr.simple.http.server.resolver;

import java.util.List;
import java.util.Map;

import com.iprogrammerr.simple.http.server.model.PathVariable;
import com.iprogrammerr.simple.http.server.parser.UrlParser;

public class PathVariablesRequirements {

    private final Map<String, Class> requiredPathVariables;

    public PathVariablesRequirements(String urlPattern, UrlParser urlParser) {
	requiredPathVariables = urlParser.getRequiredUrlPathVariables(urlPattern);
    }

    public boolean validate(List<PathVariable> pathVariables) {
	for (Map.Entry<String, Class> neededPathVariable : requiredPathVariables.entrySet()) {
	    for (PathVariable pathVariable : pathVariables) {
		if (!isPathVariableValid(neededPathVariable, pathVariable)) {
		    return false;
		}
	    }
	}
	return true;
    }

    private boolean isPathVariableValid(Map.Entry<String, Class> neededPathVariable, PathVariable pathVariable) {
	return pathVariable.getKey().equals(neededPathVariable.getKey())
		&& neededPathVariable.getValue().isAssignableFrom(pathVariable.getValue().getClass());
    }

    public boolean has() {
	return !requiredPathVariables.isEmpty();
    }

}
