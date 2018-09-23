package com.iprogrammerr.bright.server.binary.processed;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public final class GzipCompressedBinary implements CompressedBinary {

    private final byte[] source;

    public GzipCompressedBinary(byte[] source) {
	this.source = source;
    }

    @Override
    public byte[] content() throws Exception {
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(source.length);
	GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
	gzipOutputStream.write(source);
	gzipOutputStream.close();
	byteArrayOutputStream.close();
	return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String algorithm() {
	return "gzip";
    }

}