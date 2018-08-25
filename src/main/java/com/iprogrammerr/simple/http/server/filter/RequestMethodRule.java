package com.iprogrammerr.simple.http.server.filter;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.RequestMethod;

public class RequestMethodRule {

    private List<RequestMethod> requestsMethods = new ArrayList<>();

    private RequestMethodRule(List<RequestMethod> requestsMethods) {
	this.requestsMethods = requestsMethods;
    }

    public static RequestMethodRule create(RequestMethod... methods) {
	List<RequestMethod> requestsMethods = new ArrayList<>();
	requestsMethods.add(methods[0]);
	for (int i = 1; i < methods.length; i++) {
	    if (!requestsMethods.contains(methods[i])) {
		requestsMethods.add(methods[i]);
	    }
	}
	return new RequestMethodRule(requestsMethods);
    }

    public static RequestMethodRule createAny() {
	List<RequestMethod> requestsMethods = new ArrayList<>();
	requestsMethods.add(RequestMethod.GET);
	requestsMethods.add(RequestMethod.POST);
	requestsMethods.add(RequestMethod.PUT);
	requestsMethods.add(RequestMethod.DELETE);
	return new RequestMethodRule(requestsMethods);
    }

    public boolean isCompliant(RequestMethod requestMethod) {
	return requestsMethods.contains(requestMethod);
    }
}
