package com.iprogrammerr.bright.server.resolver;

import com.iprogrammerr.bright.server.constants.RequestMethod;
import com.iprogrammerr.bright.server.exception.PreConditionRequiredException;
import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.parser.ResolverUrlPatternParser;
import com.iprogrammerr.bright.server.response.Response;

public class RequestResolver {

    private String urlPattern;
    private RequestMethod requestMethod;
    private RequestHandler requestHandler;
    private boolean readyToHandle;
    private ResolverUrlPatternParser urlPatternParser;

    public RequestResolver(String urlPattern, RequestMethod requestMethod, ResolverUrlPatternParser urlPatternParser,
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
	if (urlPatternParser.hasParameters(urlPattern)) {
	    request.addParameters(urlPatternParser.getParameters(request.getPath()));
	}
	if (urlPatternParser.hasPathVariables(urlPattern)) {
	    request.addPathVariables(urlPatternParser.getPathVariables(request.getPath(), urlPattern));
	}
	readyToHandle = false;
	return requestHandler.handle(request);
    }

}
