package com.iprogrammerr.simpleserver.resolver;

import com.iprogrammerr.simpleserver.constants.RequestMethod;
import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

public class RequestResolver {

    private String url;
    private RequestMethod requestMethod;
    private RequestHandler requestHandler;

    public RequestResolver(String url, RequestMethod requestMethod, RequestHandler requestHandler) {
	this.url = url;
	this.requestMethod = requestMethod;
	this.requestHandler = requestHandler;
    }

    public boolean canHandle(String url, RequestMethod requestMethod) {
	return this.url.equals(url) && this.requestMethod.equals(requestMethod);
    }

    public void handle(Request request, Response response) {
	requestHandler.handle(request, response);
    }
}
