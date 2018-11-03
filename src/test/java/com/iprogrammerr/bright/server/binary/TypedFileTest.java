package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.type.TypedFile;

public final class TypedFileTest {

	@Test
	public void canRead() throws Exception {
		File file = new File(TypedFileTest.class.getResource("/test.html").getFile());
		assertThat(new TypedFile(file), new TypedBinaryThatCanRead(file, "html"));
	}
}
