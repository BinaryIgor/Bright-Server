package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.model.Pairs;

public class ResolvedRequest extends RequestEnvelope implements MatchedRequest {

    private Pairs parameters;
    private Pairs pathVariables;

    public ResolvedRequest(Request request, Pairs parameters, Pairs pathVariables) {
	super(request);
	this.parameters = parameters;
	this.pathVariables = pathVariables;
    }

    @Override
    public <T> T parameter(String key, Class<T> clazz) throws Exception {
	return parameters.get(key, clazz);
    }

    @Override
    public <T> T pathVariable(String key, Class<T> clazz) throws Exception {
	return pathVariables.get(key, clazz);
    }

}
