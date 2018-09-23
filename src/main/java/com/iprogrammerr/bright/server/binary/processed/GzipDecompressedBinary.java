package com.iprogrammerr.bright.server.binary.processed;

import java.io.ByteArrayInputStream;
import java.util.zip.GZIPInputStream;

import com.iprogrammerr.bright.server.binary.PacketsBinary;

public final class GzipDecompressedBinary implements DecompressedBinary {

    private final byte[] compressed;
    private final long originalLength;

    public GzipDecompressedBinary(byte[] compressed, long originalLength) {
	this.compressed = compressed;
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
