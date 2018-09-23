package com.iprogrammerr.bright.server.binary;

import java.io.InputStream;

public final class OnePacketBinary implements Binary {

    private final InputStream source;
    private final int notAvailablePacketSize;

    public OnePacketBinary(InputStream source, int notAvailablePacketSize) {
	this.source = source;
	this.notAvailablePacketSize = notAvailablePacketSize;
    }

    public OnePacketBinary(InputStream source) {
	this(source, 512);
    }

    @Override
    public byte[] content() throws Exception {
	int bytesAvailable = source.available();
	if (bytesAvailable == 0) {
	    bytesAvailable = notAvailablePacketSize;
	}
	byte[] buffer = new byte[bytesAvailable];
	int bytesRead = source.read(buffer);
	if (bytesRead <= 0) {
	    return new byte[0];
	}
	if (bytesRead == buffer.length) {
	    return buffer;
	}
	byte[] readBytes = new byte[bytesRead];
	for (int i = 0; i < bytesRead; i++) {
	    readBytes[i] = buffer[i];
	}
	return readBytes;
    }

}
