package com.iprogrammerr.bright.server.method;

public final class PostMethod implements RequestMethod {

    private static final String POST = "POST";

    @Override
    public boolean is(String requestMethod) {
	return POST.equalsIgnoreCase(requestMethod);
    }

}
