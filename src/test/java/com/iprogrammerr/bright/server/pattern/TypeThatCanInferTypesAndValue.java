package com.iprogrammerr.bright.server.pattern;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class TypeThatCanInferTypesAndValue extends TypeSafeMatcher<Type> {

	private final Map<Class, String> typesValues;
	private final Map<String, Object> values;

	public TypeThatCanInferTypesAndValue(Map<Class, String> typeValues, Map<String, Object> values) {
		this.typesValues = typeValues;
		this.values = values;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Type item) {
		boolean matched = Boolean.class.equals(item.type("boolean"))
				&& Integer.class.equals(item.type("int")) && Long.class.equals(item.type("long"))
				&& Float.class.equals(item.type("float"))
				&& Double.class.equals(item.type("double"))
				&& String.class.equals(item.type("string"));
		if (matched) {
			try {
				for (Map.Entry<Class, String> entry : this.typesValues.entrySet()) {
					if (!this.values.get(entry.getValue())
							.equals(item.value(entry.getKey(), entry.getValue()))) {
						matched = false;
						break;
					}
				}
			} catch (Exception e) {
				matched = false;
			}
		}
		return matched;
	}
}
