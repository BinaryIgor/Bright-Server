package com.iprogrammerr.bright.server.binary.processed;

import java.util.zip.Deflater;

public final class DeflateCompressedBinary implements CompressedBinary {

    private final byte[] source;

    public DeflateCompressedBinary(byte[] source) {
	this.source = source;
    }

    @Override
    public byte[] content() throws Exception {
	Deflater deflater = new Deflater();
	deflater.setInput(this.source);
	deflater.finish();
	byte[] buffer = new byte[this.source.length];
	int newLength = deflater.deflate(buffer);
	byte[] compressed = new byte[newLength];
	deflater.end();
	for (int i = 0; i < newLength; i++) {
	    compressed[i] = buffer[i];
	}
	return compressed;
    }

    @Override
    public String algorithm() {
	return "deflate";
    }

}
