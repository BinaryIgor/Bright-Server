package com.iprogrammerr.bright.server.respondent;

import java.util.ArrayList;

import com.iprogrammerr.bright.server.constants.RequestMethod;
import com.iprogrammerr.bright.server.exception.PreConditionRequiredException;
import com.iprogrammerr.bright.server.model.Pairs;
import com.iprogrammerr.bright.server.pattern.TypedUrlPattern;
import com.iprogrammerr.bright.server.pattern.UrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.request.ResolvedRequest;
import com.iprogrammerr.bright.server.response.Response;

public class HttpRespondent implements ConditionalRespondent {

    private UrlPattern urlPattern;
    private RequestMethod requestMethod;
    private Respondent respondent;

    public HttpRespondent(UrlPattern urlPattern, RequestMethod requestMethod, Respondent respondent) {
	this.urlPattern = urlPattern;
	this.requestMethod = requestMethod;
	this.respondent = respondent;
    }

    public HttpRespondent(String urlPattern, RequestMethod requestMethod, Respondent respondent) {
	this(new TypedUrlPattern(urlPattern), requestMethod, respondent);
    }

    @Override
    public boolean canRespond(Request request) {
	if (!requestMethod.equalsByValue(request.method())) {
	    return false;
	}
	return urlPattern.match(request.url());
    }

    @Override
    public Response respond(Request request) {
	if (!canRespond(request)) {
	    throw new PreConditionRequiredException("Request have to be resolved before it can be handled");
	}
	Pairs parameters;
	if (urlPattern.hasParameters()) {
	    parameters = urlPattern.readParameters(request.url());
	} else {
	    parameters = new Pairs(new ArrayList<>());
	}
	Pairs pathVariables;
	if (urlPattern.hasPathVariables()) {
	    pathVariables = urlPattern.readPathVariables(request.url());
	} else {
	    pathVariables = new Pairs(new ArrayList<>());
	}
	return respondent.respond(new ResolvedRequest(request, parameters, pathVariables));
    }

}
