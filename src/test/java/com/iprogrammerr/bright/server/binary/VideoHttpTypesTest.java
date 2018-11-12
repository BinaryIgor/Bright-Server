package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.type.VideoHttpTypes;

public final class VideoHttpTypesTest {

	@Test
	public void canRecognizeTypes() {
		assertThat(new VideoHttpTypes(),
				new HttpTypesThatCanRecognizeTypes(Arrays.asList("avi", "mpeg", "ogv", "webm", "3gp")));
	}
}
