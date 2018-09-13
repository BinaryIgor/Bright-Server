package com.iprogrammerr.bright.server.binary;

import java.io.ByteArrayInputStream;
import java.util.zip.GZIPInputStream;

public class GzipDecompressedBinary implements DecompressedBinary {

    private byte[] compressed;
    private long originalLength;

    public GzipDecompressedBinary(byte[] decompressed, long originalLength) {
	this.compressed = decompressed;
	this.originalLength = originalLength;
    }

    @Override
    public byte[] content() throws Exception {
	try (GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(compressed))) {
	    return new PacketsBinary(inputStream, originalLength).content();
	} catch (Exception exception) {
	    throw exception;
	}
    }

    @Override
    public String algorithm() {
	return "gzip";
    }

}
