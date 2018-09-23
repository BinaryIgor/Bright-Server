package com.iprogrammerr.bright.server.response.body;

public class JsonResponseBody implements ResponseBody {

    private final byte[] content;

    public JsonResponseBody(String content) {
	this.content = content.getBytes();
    }

    @Override
    public String type() {
	return "application/json";
    }

    @Override
    public byte[] content() {
	return content;
    }

}
