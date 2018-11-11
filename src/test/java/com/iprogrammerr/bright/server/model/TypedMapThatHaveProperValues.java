package com.iprogrammerr.bright.server.model;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class TypedMapThatHaveProperValues extends TypeSafeMatcher<TypedMap> {

	private final Map<String, Class> types;
	private final Map<String, Object> values;

	public TypedMapThatHaveProperValues(Map<String, Class> types, Map<String, Object> values) {
		this.types = types;
		this.values = values;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(TypedMap item, Description description) {
		description.appendText(item.keyValues().toString());
	}

	@Override
	protected boolean matchesSafely(TypedMap item) {
		boolean matched = true;
		try {
			for (Map.Entry<String, Class> entry : this.types.entrySet()) {
				matched = new TypedMapValuesMapping(entry.getKey()).value(item, entry.getValue())
						.equals(this.values.get(entry.getKey()));
				if (!matched) {
					break;
				}
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
