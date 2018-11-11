package com.iprogrammerr.bright.server.protocol;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.request.Request;

public final class RequestResponseProtocolThatCanDetermineConnectionState extends TypeSafeMatcher<RequestResponseProtocol> {

	private final Request source;
	private final boolean closed;

	public RequestResponseProtocolThatCanDetermineConnectionState(Request source, boolean closed) {
		this.source = source;
		this.closed = closed;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());

	}

	@Override
	protected void describeMismatchSafely(RequestResponseProtocol item, Description description) {
		description.appendText(String.format(
				"%s that could not determine connection state based of %s", item, this.source));
	}

	@Override
	protected boolean matchesSafely(RequestResponseProtocol item) {
		boolean matched;
		try {
			matched = item.shouldClose(this.source) == this.closed;
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}
}
