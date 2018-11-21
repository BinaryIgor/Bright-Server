package com.iprogrammerr.bright.server.test;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.iprogrammerr.bright.server.binary.Binary;
import com.iprogrammerr.bright.server.header.Header;

public final class HttpBinary implements Binary {

	private static final byte[] CRLF = "\r\n".getBytes();
	private final String firstLine;
	private final List<Header> headers;
	private final byte[] body;

	public HttpBinary(String firstLine, List<Header> headers, byte[] body) {
		this.firstLine = firstLine;
		this.headers = headers;
		this.body = body;
	}

	@Override
	public byte[] content() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(this.firstLine.getBytes());
		if (!this.headers.isEmpty()) {
			baos.write(CRLF);
			baos.write(this.headers.get(0).toString().getBytes());
			for (int i = 1; i < this.headers.size(); ++i) {
				baos.write(CRLF);
				baos.write(this.headers.get(i).toString().getBytes());
			}
		}
		if (this.body.length > 0) {
			baos.write(CRLF);
			baos.write(CRLF);
			baos.write(this.body);
		}
		return baos.toByteArray();
	}
}
