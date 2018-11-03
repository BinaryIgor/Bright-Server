package com.iprogrammerr.bright.server.initialization;

import static org.junit.Assert.assertThat;

import org.junit.Test;

public final class UnreliableStickyInitializationTest {

	@Test
	public void canStickAndUnstick() throws Exception {
		assertThat(new UnreliableStickyInitialization<>(() -> {
			return new String("string" + System.currentTimeMillis());
		}), new UnreliableStickableInitializationThatCanBeUnstick<>());
	}
}
