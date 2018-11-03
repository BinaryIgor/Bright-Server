package com.iprogrammerr.bright.server;

import static org.junit.Assert.assertThat;

import org.junit.Test;

public final class BrightServerTest {

	private static final Connection MOCKED_CONNECTION = s -> {
		try {
			s.close();
		} catch (Exception e) {

		}
	};

	@Test
	public void canStartMultiple() throws Exception {
		assertThat(new BrightServer(0, MOCKED_CONNECTION),
				new ServerThatCanHaveMultipleInstances());
	}

	@Test
	public void canRefuseStartingTwice() throws Exception {
		assertThat(new BrightServer(0, MOCKED_CONNECTION), new ServerThatRefuseToStartTwice());
	}

	@Test
	public void canStartAndStop() throws Exception {
		assertThat(new BrightServer(0, MOCKED_CONNECTION), new ServerThatCanBeRestarted());
	}
}
