package com.iprogrammerr.bright.server.binary;

import java.util.zip.Inflater;

//TODO is assumption of size a right one?
public class DeflateDecompressedBinary implements DecompressedBinary {

    private byte[] source;

    public DeflateDecompressedBinary(byte[] source) {
	this.source = source;
    }

    @Override
    public byte[] content() throws Exception {
	Inflater inflater = new Inflater();
	inflater.setInput(source);
	byte[] buffer = new byte[2 * source.length];
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
