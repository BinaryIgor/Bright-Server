package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.model.Attributes;
import com.iprogrammerr.bright.server.model.TypedMap;

public final class ResolvedRequest extends RequestEnvelope implements MatchedRequest {

    private final TypedMap parameters;
    private final TypedMap pathVariables;

    public ResolvedRequest(Request request, TypedMap parameters, TypedMap pathVariables) {
	super(request);
	this.parameters = parameters;
	this.pathVariables = pathVariables;
    }

    public ResolvedRequest(Request request) {
	this(request, new Attributes(), new Attributes());
    }

    @Override
    public TypedMap parameters() {
	return this.parameters;
    }

    @Override
    public TypedMap pathVariables() {
	return this.pathVariables;
    }

}
