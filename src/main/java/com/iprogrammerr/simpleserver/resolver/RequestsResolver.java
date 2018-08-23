package com.iprogrammerr.simpleserver.resolver;

import java.util.List;

import com.iprogrammerr.simpleserver.constants.RequestMethod;
import com.iprogrammerr.simpleserver.constants.ResponseCode;
import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

public class RequestsResolver {

    private static RequestsResolver instance;
    private String contextPath;
    private List<RequestResolver> resolvers;

    private RequestsResolver(String contextPath, List<RequestResolver> resolvers) {
	this.contextPath = contextPath;
	this.resolvers = resolvers;
    }

    public static void createInstance(String contextPath, List<RequestResolver> resolvers) {
	if (instance == null) {
	    instance = new RequestsResolver(contextPath, resolvers);
	}
    }

    public static RequestsResolver getInstance() {
	return instance;
    }

    public Response resolve(Request request) {
	System.out.println("RequestResolver.resolve()");
	Response response = new Response();
	if (!request.getPath().startsWith(contextPath)) {
	    response.setCode(ResponseCode.NOT_FOUND);
	    return response;
	}
	String withoutContextPath = request.getPath().substring(contextPath.length() + 1, request.getPath().length());
	RequestMethod requestMethod = RequestMethod.createFromString(request.getMethod());
	System.out.println(requestMethod);
	if (requestMethod == null) {
	    response.setCode(ResponseCode.NOT_FOUND);
	    return response;
	}
	for (RequestResolver resolver : resolvers) {
	    if (resolver.canHandle(withoutContextPath, requestMethod)) {
		resolver.handle(request, response);
		return response;
	    }
	}
	response.setCode(ResponseCode.NOT_FOUND);
	return response;
    }

    public String getContextPath() {
	return contextPath;
    }

}
