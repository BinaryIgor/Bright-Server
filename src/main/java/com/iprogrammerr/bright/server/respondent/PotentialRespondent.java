package com.iprogrammerr.bright.server.respondent;

import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.model.Attributes;
import com.iprogrammerr.bright.server.model.TypedMap;
import com.iprogrammerr.bright.server.pattern.TypedUrlPattern;
import com.iprogrammerr.bright.server.pattern.ParameterizableUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.request.ResolvedRequest;
import com.iprogrammerr.bright.server.response.Response;

public final class PotentialRespondent implements ConditionalRespondent {

	private final ParameterizableUrlPattern urlPattern;
	private final RequestMethod requestMethod;
	private final Respondent respondent;

	public PotentialRespondent(ParameterizableUrlPattern urlPattern, RequestMethod requestMethod,
			Respondent respondent) {
		this.urlPattern = urlPattern;
		this.requestMethod = requestMethod;
		this.respondent = respondent;
	}

	public PotentialRespondent(String urlPattern, RequestMethod requestMethod,
			Respondent respondent) {
		this(new TypedUrlPattern(urlPattern), requestMethod, respondent);
	}

	@Override
	public boolean areConditionsMet(Request request) {
		return this.requestMethod.is(request.method()) && this.urlPattern.isMatched(request.url());
	}

	@Override
	public Response response(Request request) throws Exception {
		if (!areConditionsMet(request)) {
			throw new Exception("Given request does not meet respondent conditions");
		}
		TypedMap parameters = this.urlPattern.hasParameters()
				? this.urlPattern.parameters(request.url())
				: new Attributes();
		TypedMap pathVariables = this.urlPattern.hasPathVariables()
				? this.urlPattern.pathVariables(request.url())
				: new Attributes();
		return this.respondent.response(new ResolvedRequest(request, parameters, pathVariables));
	}
}
