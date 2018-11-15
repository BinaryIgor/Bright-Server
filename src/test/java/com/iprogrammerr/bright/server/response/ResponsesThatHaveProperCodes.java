package com.iprogrammerr.bright.server.response;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class ResponsesThatHaveProperCodes extends TypeSafeMatcher<List<Response>> {

	private final List<Integer> codes;

	public ResponsesThatHaveProperCodes(List<Integer> codes) {
		this.codes = codes;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(List<Response> items) {
		boolean matched = true;
		if (this.codes.size() == items.size()) {
			for (int i = 0; i < this.codes.size(); ++i) {
				matched = this.codes.get(i) == items.get(i).code();
				if (!matched) {
					break;
				}
			}
		} else {
			matched = false;
		}
		return matched;
	}
}
