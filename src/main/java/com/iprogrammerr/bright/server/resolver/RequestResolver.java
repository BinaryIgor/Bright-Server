package com.iprogrammerr.bright.server.resolver;

import java.util.ArrayList;

import com.iprogrammerr.bright.server.constants.RequestMethod;
import com.iprogrammerr.bright.server.exception.PreConditionRequiredException;
import com.iprogrammerr.bright.server.model.Pairs;
import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.model.ResolvedRequest;
import com.iprogrammerr.bright.server.parser.UrlPatternParser;
import com.iprogrammerr.bright.server.response.Response;

public class RequestResolver {

    private String urlPattern;
    private RequestMethod requestMethod;
    private RequestHandler requestHandler;
    private boolean readyToHandle;
    private UrlPatternParser urlPatternParser;

    public RequestResolver(String urlPattern, RequestMethod requestMethod, UrlPatternParser urlPatternParser,
	    RequestHandler requestHandler) {
	this.urlPattern = urlPattern;
	this.requestMethod = requestMethod;
	this.urlPatternParser = urlPatternParser;
	this.requestHandler = requestHandler;
    }

    public boolean canResolve(Request request) {
	readyToHandle = false;
	if (!requestMethod.equalsByValue(request.getMethod())) {
	    return false;
	}
	readyToHandle = urlPatternParser.match(request.getPath(), urlPattern);
	return readyToHandle;
    }

    public Response handle(Request request) {
	if (!readyToHandle) {
	    throw new PreConditionRequiredException("Request have to be resolved before it can be handled");
	}
	Pairs parameters;
	if (urlPatternParser.hasParameters(urlPattern)) {
	    parameters = urlPatternParser.readParameters(request.getPath(), urlPattern);
	} else {
	    parameters = new Pairs(new ArrayList<>());
	}
	Pairs pathVariables;
	if (urlPatternParser.hasPathVariables(urlPattern)) {
	    pathVariables = urlPatternParser.readPathVariables(request.getPath(), urlPattern);
	} else {
	    pathVariables = new Pairs(new ArrayList<>());
	}
	readyToHandle = false;
	return requestHandler.handle(new ResolvedRequest(request, parameters, pathVariables));
    }

}
