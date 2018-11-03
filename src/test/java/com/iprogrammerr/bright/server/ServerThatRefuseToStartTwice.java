package com.iprogrammerr.bright.server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.exception.ToCatchException;

public final class ServerThatRefuseToStartTwice extends TypeSafeMatcher<Server> {

	private final ToCatchException toCatch;

	public ServerThatRefuseToStartTwice(ToCatchException toCatch) {
		this.toCatch = toCatch;
	}

	public ServerThatRefuseToStartTwice() {
		this(new ToCatchException());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Server item) {
		boolean matched = true;
		try {
			Executor executor = Executors.newSingleThreadExecutor();
			executor.execute(() -> {
				try {
					Thread.sleep(10);
					if (!item.isRunning()) {
						Thread.sleep(10);
					}
					if (!item.isRunning() || !this.toCatch.hasCatched(item::start)) {
						throw new RuntimeException(
								"Server should be running and throwing exception on start try");
					}
					item.stop();
				} catch (Exception e) {

				}
			});
			item.start();
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
