package com.iprogrammerr.bright.server.method;

public class DeleteMethod implements RequestMethod {

    private static final String DELETE = "DELETE";

    @Override
    public boolean is(String requestMethod) {
	return DELETE.equalsIgnoreCase(requestMethod);
    }

}
