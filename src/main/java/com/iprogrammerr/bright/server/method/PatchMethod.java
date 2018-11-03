package com.iprogrammerr.bright.server.method;

public final class PatchMethod implements RequestMethod {

	private static final String PATCH = "PATCH";

	@Override
	public boolean is(String method) {
		return PATCH.equalsIgnoreCase(method);
	}
}
