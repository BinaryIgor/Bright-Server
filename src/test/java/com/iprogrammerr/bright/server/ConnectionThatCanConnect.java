package com.iprogrammerr.bright.server;

import java.net.Socket;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class ConnectionThatCanConnect extends TypeSafeMatcher<Connection> {

	private final Socket source;

	public ConnectionThatCanConnect(Socket source) {
		this.source = source;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Connection item) {
		boolean matched;
		try {
			item.connect(this.source);
			matched = true;
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
