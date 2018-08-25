package com.iprogrammerr.simple.http.server.resolver;

import java.util.List;

import com.iprogrammerr.simple.http.server.constants.RequestMethod;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.model.PathVariable;
import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.model.Response;
import com.iprogrammerr.simple.http.server.parser.UrlParser;

public class RequestResolver {

    private String urlPattern;
    private RequestMethod requestMethod;
    private RequestHandler requestHandler;
    private ParametersRequirements parametersRequirements;
    private PathVariablesRequirements pathVariablesRequirements;
    private boolean readyToHandle;
    private UrlParser urlParser;

    public RequestResolver(String urlPattern, RequestMethod requestMethod, RequestHandler requestHandler) {
	this.urlPattern = urlPattern;
	this.requestMethod = requestMethod;
	this.urlParser = UrlParser.getInstance();
	this.pathVariablesRequirements = new PathVariablesRequirements(urlPattern, urlParser);
	this.parametersRequirements = new ParametersRequirements(urlPattern, urlParser);
	this.requestHandler = requestHandler;
    }

    public boolean canHandle(RequestMethod requestMethod, Request request) {
	if (parametersRequirements.has() && !parametersRequirements.validate(request)) {
	    readyToHandle = false;
	    return false;
	}
	boolean willHandle = this.requestMethod.equals(requestMethod) && validateUrl(request.getPath());
	System.out.println("Will handle?" + willHandle);
	if (willHandle) {
	    readyToHandle = true;
	}
	return willHandle;
    }

    private boolean validateUrl(String url) {
	url = urlParser.getWithoutParametersUrl(url);
	System.out.println("Without parameters url = " + url);
	if (!pathVariablesRequirements.has()) {
	    return this.urlPattern.equals(url);
	}
	return urlParser.areEqual(url, urlPattern);
    }

    public void handle(Request request, Response response) {
	if (!readyToHandle) {
	    throw new IllegalStateException();
	}
	if (!pathVariablesRequirements.has()) {
	    requestHandler.handle(request, response);
	    readyToHandle = false;
	    return;
	}
	List<PathVariable> pathVariables = urlParser.getPathVariables(request.getPath(), urlPattern);
	if (pathVariablesRequirements.validate(pathVariables)) {
	    request.setPathVariables(pathVariables);
	    requestHandler.handle(request, response);
	    readyToHandle = false;
	    // TODO clear?
	} else {
	    // TODO who should handle it then?
	    response.setCode(ResponseCode.BAD_REQUEST);
	}
    }
}
