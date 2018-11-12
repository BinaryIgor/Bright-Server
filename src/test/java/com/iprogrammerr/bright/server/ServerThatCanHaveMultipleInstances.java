package com.iprogrammerr.bright.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class ServerThatCanHaveMultipleInstances extends TypeSafeMatcher<BrightServer> {

	private static final Connection MOCKED_CONNECTION = s -> {
		try {
			s.close();
		} catch (Exception e) {

		}
	};
	private final Random random;

	public ServerThatCanHaveMultipleInstances(Random random) {
		this.random = random;
	}

	public ServerThatCanHaveMultipleInstances() {
		this(new Random());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(BrightServer item) {
		boolean matched;
		try {
			CountDownLatch latch = new CountDownLatch(1);
			int instances = 2 + this.random.nextInt(10);
			ExecutorService executor = Executors.newFixedThreadPool(instances);
			AtomicInteger count = new AtomicInteger();
			List<Server> servers = new ArrayList<>();
			servers.add(item);
			for (int i = 0; i < instances; ++i) {
				Server server = new BrightServer(0, MOCKED_CONNECTION);
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
			Thread.sleep(10);
			if (count.get() != instances) {
				Thread.sleep(20);
			}
			matched = count.get() == instances;
			for (Server s : servers) {
				s.stop();
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
