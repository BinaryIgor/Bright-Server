package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.Binary;
import com.iprogrammerr.bright.server.binary.PacketsBinary;
import com.iprogrammerr.bright.server.binary.processed.GzipCompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.GzipDecompressedBinary;

public class GzipBinaryTest {

    @Test
    public void compressDecompress() throws Exception {
	File indexHtml = new File(getClass().getResource("/test.html").getFile());
	InputStream origin = new FileInputStream(indexHtml);
	Binary binary = new PacketsBinary(origin, indexHtml.length());
	byte[] original = binary.content();
	origin.close();
	GzipCompressedBinary compressedBinary = new GzipCompressedBinary(original);
	byte[] compressed = compressedBinary.content();
	GzipDecompressedBinary decompressedBinary = new GzipDecompressedBinary(compressed, original.length);
	byte[] decompressed = decompressedBinary.content();
	assertTrue(Arrays.equals(original, decompressed));
    }
}
