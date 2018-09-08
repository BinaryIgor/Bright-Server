package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.BinaryFile;
import com.iprogrammerr.bright.server.binary.CompressedBinary;
import com.iprogrammerr.bright.server.binary.DecompressedBinary;
import com.iprogrammerr.bright.server.binary.DeflateCompressedBinary;
import com.iprogrammerr.bright.server.binary.DeflateDecompressedBinary;
import com.iprogrammerr.bright.server.binary.TypedFile;

public class DeflateBinaryTest {

    @Test
    public void deflate() throws Exception {
	File indexHtml = new File(DeflateBinaryTest.class.getResource("/test.html").getFile());
	BinaryFile binaryFile = new TypedFile(indexHtml);
	byte[] source = binaryFile.content();
	CompressedBinary compressedBinary = new DeflateCompressedBinary(source);
	byte[] compressed = compressedBinary.content();
	DecompressedBinary decompressedBinary = new DeflateDecompressedBinary(compressed);
	byte[] decompressed = decompressedBinary.content();
	assertEquals(source.length, decompressed.length);
    }
}
