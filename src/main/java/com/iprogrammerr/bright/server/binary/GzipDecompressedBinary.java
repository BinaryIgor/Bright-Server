package com.iprogrammerr.bright.server.binary;

import java.io.ByteArrayInputStream;
import java.util.zip.GZIPInputStream;

//TODO buffer lenght, bigger files?
public class GzipDecompressedBinary implements DecompressedBinary {

    private byte[] source;
    private int decompressedLength;

    public GzipDecompressedBinary(byte[] source) {
	this.source = source;
    }

    @Override
    public byte[] content() throws Exception {
	try (GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(source))) {
	    return new ScatteredBinary(inputStream, decompressedLength).content();
	} catch (Exception exception) {
	    throw exception;
	}
    }

    @Override
    public String algorithm() {
	return "gzip";
    }

}
