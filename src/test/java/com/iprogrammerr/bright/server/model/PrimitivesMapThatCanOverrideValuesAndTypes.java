package com.iprogrammerr.bright.server.model;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class PrimitivesMapThatCanOverrideValuesAndTypes extends TypeSafeMatcher<Primitives> {

	private final List<String> keys;

	public PrimitivesMapThatCanOverrideValuesAndTypes(List<String> keys) {
		this.keys = keys;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());

	}

	@Override
	protected boolean matchesSafely(Primitives item) {
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

	private boolean isOverriden(String key, Primitives item) {
		boolean overriden;
		try {
			if (item.has(key, Boolean.class)) {
				item.put(key, 1);
				overriden = item.numberValue(key).intValue() == 1;
			} else if (item.has(key, Number.class)) {
				item.put(key, "string");
				overriden = item.stringValue(key).equals("string");
			} else {
				item.put(key, true);
				overriden = item.booleanValue(key) == true;
			}
		} catch (Exception e) {
			overriden = false;
		}
		return overriden;
	}
}
