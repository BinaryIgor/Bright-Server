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

public final class ServerTest {

    private static final Connection CONNECTION_MOCK = s -> {
	try {
	    s.close();
	} catch (Exception e) {

	}
    };

    @Test
    public void canStartMultiple() throws Exception {
	CountDownLatch latch = new CountDownLatch(1);
	int instances = 5;
	Executor executor = Executors.newFixedThreadPool(instances);
	AtomicInteger count = new AtomicInteger();
	List<Server> servers = new ArrayList<>();
	for (int i = 0; i < instances; ++i) {
	    Server server = new Server(0, CONNECTION_MOCK);
	    servers.add(server);
	    executor.execute(() -> {
		try {
		    latch.await();
		    count.incrementAndGet();
		    server.start();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    });
	}
	latch.countDown();
	sleepRandomly(10);
	if (count.get() != instances) {
	    sleepRandomly(10);
	}
	assertTrue(count.get() == instances);
	for (Server s : servers) {
	    s.stop();
	}
    }

    @Test
    public void canRefuseStartingTwice() throws Exception {
	Server server = new Server(0, CONNECTION_MOCK);
	Executor executor = Executors.newSingleThreadExecutor();
	executor.execute(() -> {
	    sleepRandomly(5);
	    if (!server.isRunning()) {
		sleepRandomly(5);
	    }
	    assertTrue(server.isRunning());
	    new ToCatchException().hasCatched(server::start);
	    server.stop();
	});
	server.start();
    }

    @Test
    public void canStartAndStop() throws Exception {
	Server server = new Server(0, CONNECTION_MOCK);
	Executor executor = Executors.newSingleThreadExecutor();
	executor.execute(() -> {
	    try {
		sleepRandomly(5);
		if (!server.isRunning()) {
		    sleepRandomly(5);
		}
		assertTrue(server.isRunning());
		server.stop();
		sleepRandomly(10);
		server.stop();
	    } catch (Exception e) {

	    }
	});
	server.start();
	assertFalse(server.isRunning());
	server.start();
	assertFalse(server.isRunning());
    }

    private void sleepRandomly(int min) {
	try {
	    Thread.sleep(min + ((long) Math.random() * 3 * min));
	} catch (Exception e) {

	}
    }

}
