package com.iprogrammerr.bright.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.iprogrammerr.bright.server.exception.ToCatchException;

public final class BrightServerTest {

    private static final Connection CONNECTION_MOCK = s -> {
	try {
	    s.close();
	} catch (Exception e) {

	}
    };

    @Test
    public void canStartMultiple() throws Exception {
	CountDownLatch latch = new CountDownLatch(1);
	int instances = 2 + ((int) (Math.random() * 10));
	Executor executor = Executors.newFixedThreadPool(instances);
	AtomicInteger count = new AtomicInteger();
	List<BrightServer> servers = new ArrayList<>();
	for (int i = 0; i < instances; ++i) {
	    BrightServer server = new BrightServer(0, CONNECTION_MOCK);
	    servers.add(server);
	    executor.execute(() -> {
		try {
		    latch.await();
		    count.incrementAndGet();
		    server.start();
		} catch (Exception e) {

		}
	    });
	}
	latch.countDown();
	sleepRandomly();
	if (count.get() != instances) {
	    sleepRandomly();
	}
	assertTrue(count.get() == instances);
	for (BrightServer s : servers) {
	    s.stop();
	}
    }

    @Test
    public void canRefuseStartingTwice() throws Exception {
	BrightServer server = new BrightServer(0, CONNECTION_MOCK);
	Executor executor = Executors.newSingleThreadExecutor();
	executor.execute(() -> {
	    sleepRandomly();
	    if (!server.isRunning()) {
		sleepRandomly();
	    }
	    assertTrue(server.isRunning());
	    assertTrue(new ToCatchException().hasCatched(server::start));
	    server.stop();
	});
	server.start();
    }

    @Test
    public void canStartAndStop() throws Exception {
	BrightServer server = new BrightServer(0, CONNECTION_MOCK);
	Executor executor = Executors.newSingleThreadExecutor();
	executor.execute(() -> {
	    try {
		sleepRandomly();
		if (!server.isRunning()) {
		    sleepRandomly();
		}
		assertTrue(server.isRunning());
		server.stop();
		sleepRandomly();
		if (!server.isRunning()) {
		    sleepRandomly();
		}
		server.stop();
	    } catch (Exception e) {

	    }
	});
	server.start();
	assertFalse(server.isRunning());
	server.start();
	assertFalse(server.isRunning());
    }

    private void sleepRandomly() {
	try {
	    Thread.sleep(10 + ((long) Math.random() * 10));
	} catch (Exception e) {

	}
    }

}
