package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.bright.server.binary.pattern.HeadBodyPattern;

public final class HeadBodyPatternTest {

	@Test
	public void canFindIndex() {
		assertThat(new HeadBodyPattern(), new BinaryPatternThatCanFindIndex());
	}

	@Test
	public void shouldNotFindIndex() {
		assertThat(new HeadBodyPattern().index("zbdeSecreddba".getBytes()), Matchers.equalTo(-1));
	}
}
