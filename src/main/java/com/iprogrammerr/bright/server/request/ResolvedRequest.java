package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.model.Attributes;
import com.iprogrammerr.bright.server.model.Primitives;

public final class ResolvedRequest extends RequestEnvelope implements MatchedRequest {

	private final Primitives parameters;
	private final Primitives pathVariables;

	public ResolvedRequest(Request request, Primitives parameters, Primitives pathVariables) {
		super(request);
		this.parameters = parameters;
		this.pathVariables = pathVariables;
	}

	public ResolvedRequest(Request request) {
		this(request, new Attributes(), new Attributes());
	}

	@Override
	public Primitives parameters() {
		return this.parameters;
	}

	@Override
	public Primitives pathVariables() {
		return this.pathVariables;
	}
}
