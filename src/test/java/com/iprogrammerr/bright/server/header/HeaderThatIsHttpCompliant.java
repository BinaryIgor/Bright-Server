package com.iprogrammerr.bright.server.header;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class HeaderThatIsHttpCompliant extends TypeSafeMatcher<Header> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Header item, Description description) {
		description.appendText(String.format("%s that is not http compliant", getClass().getSimpleName()));
	}

	@Override
	protected boolean matchesSafely(Header item) {
		String writable = item.writable();
		int index = writable.indexOf(":");
		boolean matched = index == item.key().length();
		if (matched) {
			String value = writable.substring(index + 1, writable.length());
			matched = value.startsWith(" ") && value.endsWith(item.value());
		}
		return matched;
	}

}
