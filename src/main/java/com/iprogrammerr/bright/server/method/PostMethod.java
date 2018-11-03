package com.iprogrammerr.bright.server.method;

public final class PostMethod implements RequestMethod {

	private static final String POST = "POST";

	@Override
	public boolean is(String method) {
		return POST.equalsIgnoreCase(method);
	}
}
