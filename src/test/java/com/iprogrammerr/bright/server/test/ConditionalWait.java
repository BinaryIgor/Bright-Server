package com.iprogrammerr.bright.server.test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ConditionalWait {

	private final int timeout;

	public ConditionalWait(int timeout) {
		this.timeout = timeout;
	}

	public void waitUntil(Callable<Boolean> callable) throws Exception {
		CountDownLatch latch = new CountDownLatch(1);
		new Thread(() -> {
			try {
				int steps = 5;
				for (int i = 0; i < steps; ++i) {
					if (callable.call()) {
						break;
					}
					Thread.sleep(this.timeout / steps);
				}
				latch.countDown();
			} catch (Exception e) {

			}
		}).start();
		if (!latch.await(this.timeout, TimeUnit.MILLISECONDS)) {
			throw new Exception(String.format("Runnable has not finished in %d ms", this.timeout));
		}
	}
}
