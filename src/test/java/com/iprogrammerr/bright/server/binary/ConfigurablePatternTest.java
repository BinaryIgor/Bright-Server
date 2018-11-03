package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.bright.server.binary.pattern.BinaryPattern;
import com.iprogrammerr.bright.server.binary.pattern.ConfigurablePattern;

public final class ConfigurablePatternTest {

	@Test
	public void canFindIndex() {
		assertThat(new ConfigurablePattern("\r\n".getBytes()), new BinaryPatternThatCanFindIndex());
	}

	@Test
	public void shouldNotFindIndex() {
		BinaryPattern pattern = new ConfigurablePattern("abc".getBytes());
		assertThat(pattern.index("zbdeSecreddba".getBytes()), Matchers.equalTo(-1));
	}
}
