package com.iprogrammerr.bright.server.response;

import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class IntermediateResponseThatCanForwardAndBlock extends TypeSafeMatcher<IntermediateResponse> {

	private final Optional<Response> response;

	private IntermediateResponseThatCanForwardAndBlock(Optional<Response> response) {
		this.response = response;
	}

	public IntermediateResponseThatCanForwardAndBlock(Response response) {
		this(Optional.of(response));
	}

	public IntermediateResponseThatCanForwardAndBlock() {
		this(Optional.empty());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(IntermediateResponse item, Description description) {
		description.appendText(
				String.format("%s that can not return error if can not forward", getClass().getSimpleName()));
	}

	@Override
	protected boolean matchesSafely(IntermediateResponse item) {
		boolean matched = false;
		try {
			Response error = item.error();
			if (!item.canForward()) {
				matched = error.equals(this.response.get());
			} else {
				matched = false;
			}
		} catch (Exception e) {
			matched = item.canForward();
		}
		return matched;
	}
}
