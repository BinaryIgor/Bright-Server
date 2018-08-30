package com.iprogrammerr.simple.http.server.resolver;

import com.iprogrammerr.simple.http.server.constants.RequestMethod;
import com.iprogrammerr.simple.http.server.exception.PreConditionRequiredException;
import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.parser.ResolverUrlPatternParser;
import com.iprogrammerr.simple.http.server.response.Response;

public class RequestResolver {

    private String urlPattern;
    private RequestMethod requestMethod;
    private RequestHandler requestHandler;
    private boolean readyToHandle;
    private ResolverUrlPatternParser urlParser;

    public RequestResolver(String urlPattern, RequestMethod requestMethod, ResolverUrlPatternParser urlParser,
	    RequestHandler requestHandler) {
	this.urlPattern = urlPattern;
	this.requestMethod = requestMethod;
	this.urlParser = urlParser;
	this.requestHandler = requestHandler;
    }

    public boolean canResolve(Request request) {
	readyToHandle = false;
	if (!requestMethod.equalsByValue(request.getMethod())) {
	    return false;
	}
	boolean willHandle = urlParser.match(request.getPath(), urlPattern);
	if (willHandle) {
	    readyToHandle = true;
	}
	return willHandle;
    }

    public Response handle(Request request) {
	if (!readyToHandle) {
	    throw new PreConditionRequiredException("Request have to be resolved before it can be handled");
	}
	if (urlParser.hasParameters(urlPattern)) {
	    request.addParameters(urlParser.getParameters(request.getPath()));
	}
	if (urlParser.hasPathVariables(urlPattern)) {
	    request.addPathVariables(urlParser.getPathVariables(request.getPath(), urlPattern));
	}
	readyToHandle = false;
	return requestHandler.handle(request);
    }

}
