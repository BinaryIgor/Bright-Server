package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.hamcrest.Matchers;
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

	@Test
	public void shouldThrowExceptionOnNegativeToReadValue() throws Exception {
		byte[] content = new MockedBinary(10_000).content();
		boolean thrown = false;
		try (InputStream is = new BufferedInputStream(new ByteArrayInputStream(content))) {
			new PacketsBinary(is, content, content.length - 10).content();
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown, Matchers.equalTo(true));
	}
}
