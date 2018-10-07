package com.iprogrammerr.bright.server.response.body;

public final class JsonResponseBody implements ResponseBody {

    private final byte[] content;

    public JsonResponseBody(byte[] content) {
	this.content = content;
    }

    public JsonResponseBody(String content) {
	this(content.getBytes());
    }

    @Override
    public String type() {
	return "application/json";
    }

    @Override
    public byte[] content() {
	return this.content;
    }

}
