package com.iprogrammerr.simpleserver.resolver;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simpleserver.constants.RequestMethod;
import com.iprogrammerr.simpleserver.filter.RequestFilter;
import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

public class RequestsResolver {

    private static RequestsResolver instance;
    private String contextPath;
    private List<RequestResolver> resolvers;
    private List<RequestFilter> filters;

    private RequestsResolver(String contextPath, List<RequestResolver> resolvers, List<RequestFilter> filters) {
	this.contextPath = contextPath;
	this.resolvers = resolvers;
	this.filters = filters;
    }

    public static void createInstance(String contextPath, List<RequestResolver> resolvers) {
	if (instance == null) {
	    instance = new RequestsResolver(contextPath, resolvers, new ArrayList<>());
	}
    }

    public static void createInstance(String contextPath, List<RequestResolver> resolvers,
	    List<RequestFilter> filters) {
	if (instance == null) {
	    instance = new RequestsResolver(contextPath, resolvers, filters);
	}
    }

    public static RequestsResolver getInstance() {
	return instance;
    }

    public Response resolve(Request request) {
	System.out.println("RequestResolver.resolve()");
	Response response = new Response();
	if (!request.getPath().startsWith(contextPath)) {
	    return response;
	}
	String withoutContextPath = request.getPath().substring(contextPath.length() + 1, request.getPath().length());
	RequestMethod requestMethod = RequestMethod.createFromString(request.getMethod());
	System.out.println(requestMethod);
	if (requestMethod == null) {
	    return response;
	}
	RequestResolver resolver = getResolver(withoutContextPath, requestMethod);
	if (resolver == null) {
	    return response;
	}
	RequestFilter filter = getFilter(withoutContextPath, requestMethod);
	if (filter == null || filter.isValid(request, response)) {
	    resolver.handle(request, response);
	}
	return response;
    }

    private RequestResolver getResolver(String withoutContextPath, RequestMethod requestMethod) {
	for (RequestResolver resolver : resolvers) {
	    if (resolver.canHandle(withoutContextPath, requestMethod)) {
		return resolver;
	    }
	}
	return null;
    }

    private RequestFilter getFilter(String withoutContextPath, RequestMethod requestMethod) {
	for (RequestFilter filter : filters) {
	    System.out.println("???");
	    if (filter.shouldFilter(withoutContextPath, requestMethod)) {
		return filter;
	    }
	}
	return null;
    }

    public String getContextPath() {
	return contextPath;
    }

}
