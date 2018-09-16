package com.iprogrammerr.bright.server.response.body;

public class TypedResponseBody implements ResponseBody {

    private final String type;
    private final byte[] content;

    public TypedResponseBody(String type, byte[] content) {
	this.type = type;
	this.content = content;
    }

    @Override
    public String contentType() {
	return type;
    }

    @Override
    public byte[] content() {
	return content;
    }

}
