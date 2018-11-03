package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.template.ContentLengthHeader;
import com.iprogrammerr.bright.server.header.template.ContentTypeHeader;
import com.iprogrammerr.bright.server.response.body.ResponseBody;

public final class ContentResponse implements Response {

	private final int code;
	private final byte[] body;
	private final List<Header> headers;

	public ContentResponse(int code, String contentType, byte[] body, List<Header> headers) {
		this.code = code;
		this.body = body;
		this.headers = headers;
		this.headers.add(new ContentTypeHeader(contentType));
		this.headers.add(new ContentLengthHeader(body.length));
	}

	public ContentResponse(int code, ResponseBody body) {
		this(code, body.type(), body.content(), new ArrayList<>());
	}

	public ContentResponse(int code, ResponseBody body, Header... headers) {
		this(code, body.type(), body.content(), new ArrayList<>(Arrays.asList(headers)));
	}

	public ContentResponse(int code, ResponseBody body, List<Header> headers) {
		this(code, body.type(), body.content(), headers);
	}

	public ContentResponse(int code, String textBody, List<Header> headers) {
		this(code, "text/plain", textBody.getBytes(), headers);
	}

	public ContentResponse(int code, String textBody, Header... headers) {
		this(code, textBody, new ArrayList<>(Arrays.asList(headers)));
	}

	@Override
	public int code() {
		return this.code;
	}

	@Override
	public List<Header> headers() {
		return this.headers;
	}

	@Override
	public byte[] body() {
		return this.body;
	}
}
