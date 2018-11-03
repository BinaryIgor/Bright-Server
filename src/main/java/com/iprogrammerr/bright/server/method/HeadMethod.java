package com.iprogrammerr.bright.server.method;

public final class HeadMethod implements RequestMethod {

	private static final String HEAD = "HEAD";

	@Override
	public boolean is(String method) {
		return HEAD.equalsIgnoreCase(method);
	}
}
