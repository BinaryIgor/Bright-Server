package com.iprogrammerr.bright.server.cors;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public final class AllowAllPreflightCors implements PreflightCors {

	private final PreflightCors base;

	private AllowAllPreflightCors(PreflightCors base) {
		this.base = base;
	}

	public AllowAllPreflightCors() {
		this(new ConfigurablePreflightCors("*", "*", "*"));
	}

	@Override
	public boolean isValid(Request request) {
		return this.base.isValid(request);
	}

	@Override
	public List<Header> toAddHeaders() {
		return this.base.toAddHeaders();
	}

	@Override
	public boolean is(Request request) {
		return this.base.is(request);
	}
}
