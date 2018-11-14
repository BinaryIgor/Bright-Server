package com.iprogrammerr.bright.server.test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConditionalWait {

	private static final int STEPS = 5;
	private final Executor executor;
	private final int timeout;

	public ConditionalWait(Executor executor, int timeout) {
		this.executor = executor;
		this.timeout = timeout;
	}

	public ConditionalWait(int timeout) {
		this(Executors.newCachedThreadPool(), timeout);
	}

	public void waitUntil(Callable<Boolean> callable) throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		this.executor.execute(() -> {
			try {
				for (int i = 0; i < STEPS; ++i) {
					if (callable.call()) {
						break;
					}
					Thread.sleep(this.timeout / STEPS);
				}
				latch.countDown();
			} catch (Exception e) {

			}
		});
		if (!latch.await(this.timeout, TimeUnit.MILLISECONDS)) {
			throw new Exception(String.format("Callable has not finished in %d ms", this.timeout));
		}
	}
}
