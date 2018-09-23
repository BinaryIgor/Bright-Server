package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.processed.CompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.DecompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.DeflateCompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.DeflateDecompressedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedFile;

public class DeflateBinaryTest {

    @Test
    public void compressDecompress() throws Exception {
	File indexHtml = new File(getClass().getResource("/test.html").getFile());
	TypedBinary binaryFile = new TypedFile(indexHtml);
	byte[] source = binaryFile.content();
	CompressedBinary compressedBinary = new DeflateCompressedBinary(source);
	byte[] compressed = compressedBinary.content();
	DecompressedBinary decompressedBinary = new DeflateDecompressedBinary(compressed, source.length);
	byte[] decompressed = decompressedBinary.content();
	assertTrue(Arrays.equals(source, decompressed));
    }
}
