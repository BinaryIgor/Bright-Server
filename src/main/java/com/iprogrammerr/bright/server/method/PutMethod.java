package com.iprogrammerr.bright.server.method;

public final class PutMethod implements RequestMethod {

    private static final String PUT = "PUT";

    @Override
    public boolean is(String requestMethod) {
	return PUT.equalsIgnoreCase(requestMethod);
    }

}
