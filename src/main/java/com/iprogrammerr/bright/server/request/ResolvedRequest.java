package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.model.KeysValues;
import com.iprogrammerr.bright.server.model.StringsObjects;

public final class ResolvedRequest extends RequestEnvelope implements MatchedRequest {

    private final KeysValues parameters;
    private final KeysValues pathVariables;

    public ResolvedRequest(Request request, KeysValues parameters, KeysValues pathVariables) {
	super(request);
	this.parameters = parameters;
	this.pathVariables = pathVariables;
    }

    public ResolvedRequest(Request request) {
	this(request, new StringsObjects(), new StringsObjects());
    }

    @Override
    public <T> T parameter(String key, Class<T> clazz) throws Exception {
	return parameters.value(key, clazz);
    }

    @Override
    public <T> T pathVariable(String key, Class<T> clazz) throws Exception {
	return pathVariables.value(key, clazz);
    }

    @Override
    public <T> boolean hasParameter(String key, Class<T> clazz) {
	return parameters.has(key, clazz);
    }

    @Override
    public <T> boolean hasPathVariable(String key, Class<T> clazz) {
	return pathVariables.has(key, clazz);
    }

}
