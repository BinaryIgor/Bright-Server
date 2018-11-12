package com.iprogrammerr.bright.server.binary;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.binary.type.HttpTypes;

public final class HttpTypesThatCanRecognizeTypes extends TypeSafeMatcher<HttpTypes> {

	private final List<String> types;

	public HttpTypesThatCanRecognizeTypes(List<String> types) {
		this.types = types;
	}

	@Override
	protected void describeMismatchSafely(HttpTypes item, Description description) {
		description.appendText(
				String.format("%s that can not recognize one or many of %s", getClass().getSimpleName(), this.types));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(HttpTypes item) {
		boolean matched = true;
		for (String t : this.types) {
			matched = item.isKnown(t) && !item.type(t).equals(t);
			if (!matched) {
				break;
			}
		}
		return matched;
	}
}
