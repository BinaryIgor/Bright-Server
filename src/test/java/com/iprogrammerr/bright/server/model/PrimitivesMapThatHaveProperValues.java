package com.iprogrammerr.bright.server.model;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class PrimitivesMapThatHaveProperValues extends TypeSafeMatcher<Primitives> {

	private final Map<String, Boolean> booleans;
	private final Map<String, Number> numbers;
	private final Map<String, String> strings;

	public PrimitivesMapThatHaveProperValues(Map<String, Boolean> booleans, Map<String, Number> numbers,
			Map<String, String> strings) {
		this.booleans = booleans;
		this.numbers = numbers;
		this.strings = strings;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Primitives item, Description description) {
		description.appendText(item.keyValues().toString());
	}

	@Override
	protected boolean matchesSafely(Primitives item) {
		boolean matched = true;
		try {
			matched = areBooleansMatched(item) && areNumbersMatched(item) && areStringsMatched(item);
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

	private boolean areBooleansMatched(Primitives map) throws Exception {
		boolean matched = true;
		for (Map.Entry<String, Boolean> entry : this.booleans.entrySet()) {
			matched = map.booleanValue(entry.getKey()) == entry.getValue();
		}
		return matched;
	}

	private boolean areNumbersMatched(Primitives map) throws Exception {
		boolean matched = true;
		for (Map.Entry<String, Number> entry : this.numbers.entrySet()) {
			matched = map.numberValue(entry.getKey()).equals(entry.getValue());
		}
		return matched;
	}

	private boolean areStringsMatched(Primitives map) throws Exception {
		boolean matched = true;
		for (Map.Entry<String, String> entry : this.strings.entrySet()) {
			matched = map.stringValue(entry.getKey()).equals(entry.getValue());
		}
		return matched;
	}
}
