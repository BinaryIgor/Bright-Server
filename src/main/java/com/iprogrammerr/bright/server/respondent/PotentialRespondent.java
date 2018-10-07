package com.iprogrammerr.bright.server.respondent;

import java.util.ArrayList;

import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.model.KeysValues;
import com.iprogrammerr.bright.server.model.StringsObjects;
import com.iprogrammerr.bright.server.pattern.TypedUrlPattern;
import com.iprogrammerr.bright.server.pattern.UrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.request.ResolvedRequest;
import com.iprogrammerr.bright.server.response.Response;

public final class PotentialRespondent implements ConditionalRespondent {

    private final UrlPattern urlPattern;
    private final RequestMethod requestMethod;
    private final Respondent respondent;

    public PotentialRespondent(UrlPattern urlPattern, RequestMethod requestMethod, Respondent respondent) {
	this.urlPattern = urlPattern;
	this.requestMethod = requestMethod;
	this.respondent = respondent;
    }

    public PotentialRespondent(String urlPattern, RequestMethod requestMethod, Respondent respondent) {
	this(new TypedUrlPattern(urlPattern), requestMethod, respondent);
    }

    @Override
    public boolean areConditionsMet(Request request) {
	return this.requestMethod.is(request.method()) && this.urlPattern.isMatched(request.url());
    }

    @Override
    public Response response(Request request) throws Exception {
	if (!areConditionsMet(request)) {
	    throw new Exception("Given request does not meet respondent condtions");
	}
	KeysValues parameters;
	if (this.urlPattern.hasParameters()) {
	    parameters = this.urlPattern.parameters(request.url());
	} else {
	    parameters = new StringsObjects(new ArrayList<>());
	}
	KeysValues pathVariables;
	if (this.urlPattern.hasPathVariables()) {
	    pathVariables = this.urlPattern.pathVariables(request.url());
	} else {
	    pathVariables = new StringsObjects(new ArrayList<>());
	}
	return this.respondent.response(new ResolvedRequest(request, parameters, pathVariables));
    }

}
