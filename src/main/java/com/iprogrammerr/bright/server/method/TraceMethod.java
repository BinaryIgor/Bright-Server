package com.iprogrammerr.bright.server.method;

public final class TraceMethod implements RequestMethod {

	private static final String TRACE = "TRACE";

	@Override
	public boolean is(String method) {
		return TRACE.equalsIgnoreCase(method);
	}
}
