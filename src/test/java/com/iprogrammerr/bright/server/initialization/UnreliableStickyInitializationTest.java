package com.iprogrammerr.bright.server.initialization;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class UnreliableStickyInitializationTest {

    @Test
    public void canStickAndUnstick() throws Exception {
	UnreliableStickyInitialization<String> initialization = new UnreliableStickyInitialization<>(() -> {
	    return new String("string" + System.currentTimeMillis());
	});
	String first = initialization.value();
	assertTrue(first == initialization.value());
	initialization.unstick();
	assertFalse(first == initialization.value());
    }

}
