package com.iprogrammerr.bright.server.method;

public class HeadMethod implements RequestMethod {

    private static final String HEAD = "HEAD";

    @Override
    public boolean is(String requestMethod) {
	return HEAD.equalsIgnoreCase(requestMethod);
    }

}
