package com.iprogrammerr.bright.server.binary;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class BinaryThatCanReadContent extends TypeSafeMatcher<Binary> {

	private byte[] content;

	public BinaryThatCanReadContent(byte[] content) {
		this.content = content;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Binary item, Description description) {
		description.appendText(String.format("%s that can have content of %d length", getClass().getSimpleName(),
				this.content.length));
	}

	@Override
	protected boolean matchesSafely(Binary item) {
		boolean matched;
		try {
			matched = Arrays.equals(this.content, item.content());
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}
}
