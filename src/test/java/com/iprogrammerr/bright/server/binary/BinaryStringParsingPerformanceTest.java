package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.pattern.ConfigurablePattern;

public final class BinaryStringParsingPerformanceTest {

	@Test
	public void binaryShouldBeFasterThanString() throws Exception {
		System.out.println("BinaryStringParsingPerformanceTest.binaryShouldBeFasterThanString()");
		assertThat(new ConfigurablePattern("\r\r\n\n".getBytes()),
				new BinaryPatternThatIsFasterThanStringParsing());
	}
}
