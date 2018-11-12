package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.type.AudioHttpTypes;

public final class AudioHttpTypesTest {

	@Test
	public void canRecognizeTypes() {
		assertThat(new AudioHttpTypes(), new HttpTypesThatCanRecognizeTypes(
				Arrays.asList("aac", "mpeg", "mid", "midi", "oga", "wav", "weba", "3gp", "3g2")));
	}
}
