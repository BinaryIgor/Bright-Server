package com.iprogrammerr.bright.server.binary;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCompressedBinary implements CompressedBinary {

    private byte[] source;

    private GzipCompressedBinary(byte[] source) {
	this.source = source;
    }

    @Override
    public byte[] content() throws Exception {
	try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(source.length);
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
	    gzipOutputStream.write(source);
	    return byteArrayOutputStream.toByteArray();
	} catch (Exception exception) {
	    throw exception;
	}
    }

    @Override
    public String algorithm() {
	return "gzip";
    }

}
