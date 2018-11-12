package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import com.iprogrammerr.bright.server.test.MockedBinary;

public final class PacketsBinaryTest {

	@Test
	public void canReadContent() throws Exception {
		byte[] content = new MockedBinary(10_000).content();
		try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(content))) {
			assertThat(new PacketsBinary(is, content.length), new BinaryThatCanReadContent(content));
		}
	}
}
