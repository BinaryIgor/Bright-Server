package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.processed.DeflateCompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.DeflateDecompressedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedFile;

public class DeflateBinaryTest {

	@Test
	public void canCompressAndDecompress() throws Exception {
		TypedBinary tb = new TypedFile(new File(getClass().getResource("/test.html").getFile()));
		byte[] source = tb.content();
		assertThat(new DeflateCompressedBinary(source),
				new BinaryThatCanCompressAndDecompress(source, DeflateDecompressedBinary.class, "deflate"));
	}
}
