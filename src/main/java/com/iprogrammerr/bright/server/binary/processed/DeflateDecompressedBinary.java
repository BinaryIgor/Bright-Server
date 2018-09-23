package com.iprogrammerr.bright.server.binary.processed;

import java.util.zip.Inflater;

public final class DeflateDecompressedBinary implements DecompressedBinary {

    private final byte[] source;
    private final int decompressedSize;

    public DeflateDecompressedBinary(byte[] source, int decompressedSize) {
	this.source = source;
	this.decompressedSize = decompressedSize;
    }

    @Override
    public byte[] content() throws Exception {
	Inflater inflater = new Inflater();
	inflater.setInput(source);
	byte[] buffer = new byte[decompressedSize];
	int newLength = inflater.inflate(buffer);
	byte[] decompressed = new byte[newLength];
	for (int i = 0; i < newLength; i++) {
	    decompressed[i] = buffer[i];
	}
	return decompressed;
    }

    @Override
    public String algorithm() {
	return "deflate";
    }

}
