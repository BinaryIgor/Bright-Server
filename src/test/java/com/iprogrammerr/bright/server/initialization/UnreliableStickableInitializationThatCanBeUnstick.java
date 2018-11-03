package com.iprogrammerr.bright.server.initialization;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class UnreliableStickableInitializationThatCanBeUnstick<T> extends TypeSafeMatcher<UnreliableStickableInitialization<T>> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(UnreliableStickableInitialization<T> item) {
		boolean matched;
		try {
			T value = item.value();
			matched = value == item.value();
			if (matched) {
				item.unstick();
				matched = item.value() != value && item.value() == item.value();
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
