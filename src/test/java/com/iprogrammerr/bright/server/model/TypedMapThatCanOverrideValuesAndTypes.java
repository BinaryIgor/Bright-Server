package com.iprogrammerr.bright.server.model;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class TypedMapThatCanOverrideValuesAndTypes extends TypeSafeMatcher<TypedMap> {

	private final List<String> keys;

	public TypedMapThatCanOverrideValuesAndTypes(List<String> keys) {
		this.keys = keys;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());

	}

	@Override
	protected boolean matchesSafely(TypedMap item) {
		boolean matched;
		try {
			matched = true;
			for (String key : this.keys) {
				if (!isOverriden(key, item)) {
					matched = false;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

	private boolean isOverriden(String key, TypedMap item) {
		boolean overriden;
		try {
			if (item.has(key, Boolean.class) || item.has(key, String.class)) {
				item.put(key, 1);
				overriden = item.intValue(key) == 1;
			} else if (item.has(key, Integer.class) || item.has(key, Long.class)) {
				item.put(key, "string");
				overriden = item.stringValue(key).equals("string");
			} else if (item.has(key, Float.class) || item.has(key, Double.class)) {
				item.put(key, true);
				overriden = item.booleanValue(key) == true;
			} else {
				item.put(key, 44.5);
				overriden = item.doubleValue(key) == 44.5;
			}
		} catch (Exception e) {
			overriden = false;
		}
		return overriden;
	}
}
