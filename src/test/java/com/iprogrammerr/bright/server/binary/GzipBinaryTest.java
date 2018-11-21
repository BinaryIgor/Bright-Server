
package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.processed.GzipCompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.GzipDecompressedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedFile;

public final class GzipBinaryTest {

	@Test
	public void canCompressAndDecompress() throws Exception {
		TypedBinary tb = new TypedFile(new File(getClass().getResource("/test.html").getFile()));
		byte[] source = tb.content();
		assertThat(new GzipCompressedBinary(source),
				new BinaryThatCanCompressAndDecompress(source, GzipDecompressedBinary.class, "gzip"));
	}
}
