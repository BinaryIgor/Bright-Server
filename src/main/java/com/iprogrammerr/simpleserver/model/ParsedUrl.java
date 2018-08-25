package com.iprogrammerr.simpleserver.model;

import java.util.List;

public class ParsedUrl {

    private String url;
    private List<Parameter> parameters;
    private List<Number> pathVariables;

    public ParsedUrl(String url, List<Parameter> parameters, List<Number> pathVariables) {
	this.url = url;
	this.parameters = parameters;
	this.pathVariables = pathVariables;
    }

    public String getUrl() {
	return url;
    }

    public List<Parameter> getParameters() {
	return parameters;
    }

    public List<Number> getPathVariables() {
	return pathVariables;
    }

}
