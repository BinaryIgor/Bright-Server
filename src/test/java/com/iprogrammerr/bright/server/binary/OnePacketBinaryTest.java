package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import com.iprogrammerr.bright.server.test.MockedBinary;

public final class OnePacketBinaryTest {

	@Test
	public void canReadContent() throws Exception {
		byte[] content = new MockedBinary().content();
		try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(content))) {
			assertThat(new OnePacketBinary(is), new BinaryThatCanReadContent(content));
		}
	}

	@Test
	public void canReadEmptyStream() throws Exception {
		byte[] content = new byte[0];
		try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(content))) {
			assertThat(new OnePacketBinary(is), new BinaryThatCanReadContent(content));
		}
	}
}
