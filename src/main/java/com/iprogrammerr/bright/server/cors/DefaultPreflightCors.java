package com.iprogrammerr.bright.server.cors;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public final class DefaultPreflightCors implements PreflightCors {

	private final List<Header> headers;

	private DefaultPreflightCors(List<Header> headers) {
		this.headers = headers;
	}

	public DefaultPreflightCors() {
		this(new ArrayList<>());
	}

	@Override
	public boolean isValid(Request request) {
		return false;
	}

	@Override
	public List<Header> toAddHeaders() {
		return this.headers;
	}

	@Override
	public boolean is(Request request) {
		return false;
	}
}
