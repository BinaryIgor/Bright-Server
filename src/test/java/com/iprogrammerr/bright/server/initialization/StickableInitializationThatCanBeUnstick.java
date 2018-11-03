package com.iprogrammerr.bright.server.initialization;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class StickableInitializationThatCanBeUnstick<T> extends TypeSafeMatcher<StickableInitialization<T>> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(StickableInitialization<T> item) {
		T value = item.value();
		boolean matched = value == item.value();
		if (matched) {
			item.unstick();
			matched = item.value() != value && item.value() == item.value();
		}
		return matched;
	}
}
