package com.iprogrammerr.simpleserver.resolver;

import java.util.List;

import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

public class RequestsResolver {

    private static RequestsResolver instance;
    private List<RequestResolver> resolvers;

    private RequestsResolver(List<RequestResolver> resolvers) {
	this.resolvers = resolvers;
    }

    public static void createInstance(List<RequestResolver> resolvers) {
	if (instance == null) {
	    instance = new RequestsResolver(resolvers);
	}
    }

    public static RequestsResolver getInstance() {
	return instance;
    }

    public Response resolve(Request request) {
	RequestResolver resolver = getResolver(request.getPath());
	Response response = new Response();
	if (resolver == null) {
	    response.setCode(404);
	} else {
	    resolver.resolve(request, response);
	}
	return response;
    }

    private RequestResolver getResolver(String path) {
	for (RequestResolver resolver : resolvers) {
	    if (resolver.canResolve(path)) {
		return resolver;
	    }
	}
	return null;
    }
}
