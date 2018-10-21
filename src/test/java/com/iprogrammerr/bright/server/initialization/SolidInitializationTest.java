package com.iprogrammerr.bright.server.initialization;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public final class SolidInitializationTest {

    @Test
    public void shouldBeThreadSafe() throws Exception {
	CountDownLatch latch = new CountDownLatch(1);
	String value = "value";
	AtomicInteger stickyCalls = new AtomicInteger();
	AtomicInteger threadCalls = new AtomicInteger();
	Initialization<String> initialization = new SolidInitialization<>(() -> {
	    stickyCalls.incrementAndGet();
	    return value;
	});
	int probes = 100;
	Executor executor = Executors.newFixedThreadPool(probes);
	for (int i = 0; i < probes; ++i) {
	    executor.execute(() -> {
		try {
		    latch.await();
		    Thread.sleep((long) (Math.random() * 5));
		    initialization.value();
		    threadCalls.incrementAndGet();
		} catch (Exception e) {

		}
	    });
	}
	latch.countDown();
	Thread.sleep(5);
	while (threadCalls.get() != probes) {
	    Thread.sleep(5);
	}
	assertTrue(stickyCalls.get() == 1);
    }

}
