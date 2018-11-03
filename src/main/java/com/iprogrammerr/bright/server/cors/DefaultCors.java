package com.iprogrammerr.bright.server.cors;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public final class DefaultCors implements Cors {

	private final List<Header> headers;

	private DefaultCors(List<Header> headers) {
		this.headers = headers;
	}

	public DefaultCors() {
		this(new ArrayList<>());
	}

	@Override
	public boolean isValid(Request request) {
		return true;
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
