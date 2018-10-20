package com.iprogrammerr.bright.server.mock;

import java.io.ByteArrayOutputStream;

import com.iprogrammerr.bright.server.binary.Binary;

public final class MockedMultipartBinary implements Binary {

    private static final byte[] CRLF = "\r\n".getBytes();
    private final Binary base;

    public MockedMultipartBinary(Binary base) {
	this.base = base;
    }

    public MockedMultipartBinary() {
	this(new MockedBinary());
    }

    @Override
    public byte[] content() throws Exception {
	byte[] boundary = randomBoundary();
	int parts = 1 + (int) (Math.random() * 5);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	for (int i = 0; i < parts; ++i) {
	    baos.write(boundary);
	    baos.write(CRLF);
	    baos.write(CRLF);
	    baos.write(this.base.content());
	    if (i != (parts - 1)) {
		baos.write(boundary);
		baos.write(CRLF);
	    }
	}
	baos.write(CRLF);
	baos.write((boundary + "--").getBytes());
	return baos.toByteArray();
    }

    private byte[] randomBoundary() {
	int length = 10 + ((int) Math.random() * 100);
	byte[] boundary = new byte[length];
	for (int i = 0; i < boundary.length; ++i) {
	    boundary[i] = (byte) (48 + (Math.random() * 74));
	}
	return boundary;
    }

}
