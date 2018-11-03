package com.iprogrammerr.bright.server.binary;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.binary.pattern.BinaryPattern;

public final class BinaryPatternThatCanFindIndex extends TypeSafeMatcher<BinaryPattern> {

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(BinaryPattern item) {
		boolean matched;
		String pattern = new String(item.value());
		byte[] content = ("abcSecret" + pattern + "abcdef").getBytes();
		int index = item.index(content);
		matched = index > 0;
		if (matched) {
			matched = new String(Arrays.copyOfRange(content, index, content.length))
					.startsWith(pattern);
		}
		return matched;
	}
}
