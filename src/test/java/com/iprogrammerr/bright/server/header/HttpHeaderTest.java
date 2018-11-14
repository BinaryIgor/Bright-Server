package com.iprogrammerr.bright.server.header;

import static org.junit.Assert.assertThat;

import org.junit.Test;

public final class HttpHeaderTest {

	@Test
	public void canIgnoreCase() {
		assertThat(new HttpHeader("Content-Type", "text/html"), new HeaderThatCanIgnoreCase("content-type"));
	}

	@Test
	public void shouldBeHttpCompliant() {
		assertThat(new HttpHeader("Content-Length", "22"), new HeaderThatIsHttpCompliant());
	}
}
