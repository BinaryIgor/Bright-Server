package com.iprogrammerr.bright.server.initialization;

import static org.junit.Assert.assertThat;

import org.junit.Test;

public final class StickyInitializationTest {

	@Test
	public void canStickAndUnstick() {
		assertThat(new StickyInitialization<>(() -> {
			return new String("string" + System.currentTimeMillis());
		}), new StickableInitializationThatCanBeUnstick<>());
	}
}
