package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.model.KeysValues;

public class ResolvedRequest extends RequestEnvelope implements MatchedRequest {

    private KeysValues parameters;
    private KeysValues pathVariables;

    public ResolvedRequest(Request request, KeysValues parameters, KeysValues pathVariables) {
	super(request);
	this.parameters = parameters;
	this.pathVariables = pathVariables;
    }

    @Override
    public <T> T parameter(String key, Class<T> clazz) throws Exception {
	return parameters.value(key, clazz);
    }

    @Override
    public <T> T pathVariable(String key, Class<T> clazz) throws Exception {
	return pathVariables.value(key, clazz);
    }

}
