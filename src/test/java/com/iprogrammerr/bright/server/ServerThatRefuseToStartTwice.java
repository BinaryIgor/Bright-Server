package com.iprogrammerr.bright.server;

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
			new Thread(() -> {
				try {
					Thread.sleep(10);
					if (!item.isRunning()) {
						Thread.sleep(10);
					}
					if (!item.isRunning() || !this.toCatch.hasCatched(item::start)) {
						throw new Exception("Server should be running and throwing exception on start try");
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					item.stop();
				}
			}).start();
			item.start();
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
