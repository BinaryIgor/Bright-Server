package com.iprogrammerr.bright.server.pattern;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class TypeThatCanProbeValues extends TypeSafeMatcher<Type> {

	private final List<Object> expected;

	public TypeThatCanProbeValues(List<Object> expected) {
		this.expected = expected;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Type item, Description description) {
		description.appendText(String.format("%s that can not probe %s", item, this.expected));
	}

	@Override
	protected boolean matchesSafely(Type item) {
		boolean matched = true;
		for (Object e : this.expected) {
			if (!e.equals(item.probedValue(e.toString()))) {
				matched = false;
				break;
			}
		}
		return matched;
	}
}
