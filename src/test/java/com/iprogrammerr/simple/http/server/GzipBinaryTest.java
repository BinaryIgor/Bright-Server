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
    public void canCompressDecompress() throws Exception {
	File file = new File(getClass().getResource("/test.html").getFile());
	InputStream source = new FileInputStream(file);
	Binary binary = new PacketsBinary(source, file.length());
	byte[] original = binary.content();
	source.close();
	GzipCompressedBinary gcb = new GzipCompressedBinary(original);
	byte[] compressed = gcb.content();
	GzipDecompressedBinary gdb = new GzipDecompressedBinary(compressed, original.length);
	byte[] decompressed = gdb.content();
	assertTrue(Arrays.equals(original, decompressed));
    }
}
