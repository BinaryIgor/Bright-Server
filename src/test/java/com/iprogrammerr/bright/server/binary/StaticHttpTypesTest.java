package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.type.StaticHttpTypes;

public final class StaticHttpTypesTest {

	@Test
	public void canRecognizeTypes() {
		assertThat(new StaticHttpTypes(),
				new HttpTypesThatCanRecognizeTypes(Arrays.asList("json", "pdf", "rar", "tar", "zip", "sh", "xhtml",
						"xml", "abw", "csh", "doc", "epub", "jar", "ods", "ogx", "ppt", "xls", "jpg", "png", "gif",
						"svg", "bmp", "ico", "tif", "tiff", "webp")));
	}
}
