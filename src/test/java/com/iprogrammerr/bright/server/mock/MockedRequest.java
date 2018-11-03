package com.iprogrammerr.bright.server.mock;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public final class MockedRequest implements Request {

	private final String url;
	private final String method;

	public MockedRequest(String url, String method) {
		this.url = url;
		this.method = method;
	}

	@Override
	public String url() {
		return this.url;
	}

	@Override
	public String method() {
		return this.method;
	}

	@Override
	public List<Header> headers() {
		return new ArrayList<>();
	}

	@Override
	public boolean hasHeader(String key) {
		return false;
	}

	@Override
	public String header(String key) {
		return "";
	}

	@Override
	public byte[] body() {
		return new byte[0];
	}

	@Override
	public void removeContext(String context) {

	}
}
