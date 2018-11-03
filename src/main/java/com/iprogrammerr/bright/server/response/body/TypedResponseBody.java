package com.iprogrammerr.bright.server.response.body;

public final class TypedResponseBody implements ResponseBody {

	private final String type;
	private final byte[] content;

	public TypedResponseBody(String type, byte[] content) {
		this.type = type;
		this.content = content;
	}

	@Override
	public String type() {
		return this.type;
	}

	@Override
	public byte[] content() {
		return this.content;
	}

}
