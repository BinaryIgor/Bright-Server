package com.iprogrammerr.bright.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class ServerThatCanBeRestarted extends TypeSafeMatcher<Server> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Server item) {
		boolean matched;
		try {
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(() -> {
				if (!item.isRunning()) {
					Thread.sleep(10);
				}
				if (!item.isRunning()) {
					throw new RuntimeException("Server was supposed to run");
				}
				item.stop();
				Thread.sleep(10);
				if (!item.isRunning()) {
					Thread.sleep(10);
				}
				if (!item.isRunning()) {
					throw new RuntimeException("Server was supposed to run");
				}
				item.stop();
				return true;
			});
			item.start();
			matched = !item.isRunning();
			if (matched) {
				item.start();
				matched = !item.isRunning();
			}
		} catch (Exception e) {
			matched = false;
			if (item.isRunning()) {
				item.stop();
			}
		}
		return matched;
	}
}
