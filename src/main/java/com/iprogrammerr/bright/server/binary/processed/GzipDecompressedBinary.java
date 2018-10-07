package com.iprogrammerr.bright.server.binary.processed;

import java.io.ByteArrayInputStream;
import java.util.zip.GZIPInputStream;

import com.iprogrammerr.bright.server.binary.PacketsBinary;

public final class GzipDecompressedBinary implements DecompressedBinary {

    private final byte[] compressed;
    private final long originalSize;

    public GzipDecompressedBinary(byte[] compressed, long originalSize) {
	this.compressed = compressed;
	this.originalSize = originalSize;
    }

    @Override
    public byte[] content() throws Exception {
	try (GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(this.compressed))) {
	    return new PacketsBinary(is, this.originalSize).content();
	}
    }

    @Override
    public String algorithm() {
	return "gzip";
    }

}
