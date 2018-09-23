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

public final class HttpRespondent implements ConditionalRespondent {

    private final UrlPattern urlPattern;
    private final RequestMethod requestMethod;
    private final Respondent respondent;

    public HttpRespondent(UrlPattern urlPattern, RequestMethod requestMethod, Respondent respondent) {
	this.urlPattern = urlPattern;
	this.requestMethod = requestMethod;
	this.respondent = respondent;
    }

    public HttpRespondent(String urlPattern, RequestMethod requestMethod, Respondent respondent) {
	this(new TypedUrlPattern(urlPattern), requestMethod, respondent);
    }

    @Override
    public boolean conditionsMet(Request request) {
	return requestMethod.is(request.method()) && urlPattern.match(request.url());
    }

    @Override
    public Response respond(Request request) throws Exception {
	if (!conditionsMet(request)) {
	    throw new Exception("Given request does not meet respondent condtions");
	}
	KeysValues parameters;
	if (urlPattern.hasParameters()) {
	    parameters = urlPattern.readParameters(request.url());
	} else {
	    parameters = new StringsObjects(new ArrayList<>());
	}
	KeysValues pathVariables;
	if (urlPattern.hasPathVariables()) {
	    pathVariables = urlPattern.readPathVariables(request.url());
	} else {
	    pathVariables = new StringsObjects(new ArrayList<>());
	}
	return respondent.respond(new ResolvedRequest(request, parameters, pathVariables));
    }

}
