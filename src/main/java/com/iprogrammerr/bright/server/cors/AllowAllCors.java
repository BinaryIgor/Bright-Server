package com.iprogrammerr.bright.server.cors;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public final class AllowAllCors implements Cors {

    private final Cors base;

    public AllowAllCors() {
	this.base = new ConfigurableCors("*", "*", "*");
    }

    @Override
    public boolean isValid(Request request) {
	return this.base.isValid(request);
    }

    @Override
    public List<Header> toAddHeaders() {
	return this.base.toAddHeaders();
    }
}
