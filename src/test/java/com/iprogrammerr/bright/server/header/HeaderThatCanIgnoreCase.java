package com.iprogrammerr.bright.server.header;

import java.util.Random;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class HeaderThatCanIgnoreCase extends TypeSafeMatcher<Header> {

	private final Random random;
	private final String key;

	public HeaderThatCanIgnoreCase(Random random, String key) {
		this.random = random;
		this.key = key;
	}

	public HeaderThatCanIgnoreCase(String key) {
		this(new Random(), key);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Header item, Description description) {
		description.appendText(String.format("%s that does not ignore case", getClass().getSimpleName()));
	}

	@Override
	protected boolean matchesSafely(Header item) {
		return item.is(randomizedCase());
	}

	private String randomizedCase() {
		char[] chars = this.key.toCharArray();
		for (int i = 0; i < chars.length; ++i) {
			if (!Character.isLetter(chars[i])) {
				continue;
			}
			chars[i] = (this.random.nextInt() % 2) == 0 ? Character.toUpperCase(chars[i])
					: Character.toLowerCase(chars[i]);
		}
		return new String(chars);
	}
}
