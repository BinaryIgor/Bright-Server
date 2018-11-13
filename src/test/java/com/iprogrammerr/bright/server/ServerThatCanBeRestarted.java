package com.iprogrammerr.bright.server;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.test.ConditionalWait;

public final class ServerThatCanBeRestarted extends TypeSafeMatcher<Server> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Server item, Description description) {
		description.appendText(String.format("%s that can not be restarted", getClass().getSimpleName()));
	}

	@Override
	protected boolean matchesSafely(Server item) {
		boolean matched;
		try {
			item.start();
			ConditionalWait wait = new ConditionalWait(100);
			wait.waitUntil(() -> item.isRunning());
			item.stop();
			wait.waitUntil(() -> !item.isRunning());
			item.start();
			wait.waitUntil(() -> item.isRunning());
			item.stop();
			matched = true;
		} catch (Exception e) {
			matched = false;
			if (item.isRunning()) {
				item.stop();
			}
		}
		return matched;
	}
}
