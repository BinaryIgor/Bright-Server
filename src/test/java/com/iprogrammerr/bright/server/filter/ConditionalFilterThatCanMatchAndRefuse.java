package com.iprogrammerr.bright.server.filter;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.request.Request;

public final class ConditionalFilterThatCanMatchAndRefuse extends TypeSafeMatcher<ConditionalFilter> {

	private final List<Request> requests;
	private final boolean refuse;

	public ConditionalFilterThatCanMatchAndRefuse(List<Request> requests, boolean refuse) {
		this.requests = requests;
		this.refuse = refuse;
	}

	public ConditionalFilterThatCanMatchAndRefuse(List<Request> requests) {
		this(requests, false);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(ConditionalFilter item) {
		boolean matched = true;
		try {
			for (Request r : this.requests) {
				if (shouldStopMatching(item, r)) {
					matched = false;
					break;
				}
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}

	private boolean shouldStopMatching(ConditionalFilter filter, Request request) {
		return (this.refuse && filter.areConditionsMet(request)) || (!this.refuse && !filter.areConditionsMet(request));
	}
}
