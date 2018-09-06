package com.iprogrammerr.bright.server.request;

public class GetMethod implements RequestMethod {

    private static final String GET = "GET";

    @Override
    public boolean is(String requestMethod) {
	return GET.equalsIgnoreCase(requestMethod);
    }

}
