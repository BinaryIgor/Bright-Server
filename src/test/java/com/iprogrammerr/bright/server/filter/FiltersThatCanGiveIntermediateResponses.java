package com.iprogrammerr.bright.server.filter;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.IntermediateResponse;

public final class FiltersThatCanGiveIntermediateResponses extends TypeSafeMatcher<Filters> {

	private final List<Request> requests;
	private final IntermediateResponse response;

	public FiltersThatCanGiveIntermediateResponses(List<Request> requests, IntermediateResponse response) {
		this.requests = requests;
		this.response = response;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Filters item, Description description) {
		description.appendText(String.format("%s that can give toForward: %b response to %s requests",
				getClass().getSimpleName(), this.response.canForward(), this.requests));
	}

	@Override
	protected boolean matchesSafely(Filters item) {
		boolean matched = true;
		for (Request r : this.requests) {
			matched = areResponsesMatched(item.response(r));
			if (!matched) {
				break;
			}
		}
		return matched;
	}

	private boolean areResponsesMatched(IntermediateResponse response) {
		boolean matched = this.response.canForward() == response.canForward();
		if (matched && !this.response.canForward()) {
			try {
				matched = this.response.error().equals(response.error());
			} catch (Exception e) {
				matched = false;
			}
		}
		return matched;
	}
}
