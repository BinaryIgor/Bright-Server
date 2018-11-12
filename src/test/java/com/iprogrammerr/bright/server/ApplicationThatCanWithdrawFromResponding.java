package com.iprogrammerr.bright.server;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.application.Application;
import com.iprogrammerr.bright.server.request.Request;

public final class ApplicationThatCanWithdrawFromResponding extends TypeSafeMatcher<Application> {

	private final List<Request> requests;

	public ApplicationThatCanWithdrawFromResponding(List<Request> requests) {
		this.requests = requests;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Application item, Description description) {
		description.appendText(String.format("%s that is responding", getClass().getSimpleName()));
	}

	@Override
	protected boolean matchesSafely(Application item) {
		boolean matched = true;
		for (Request r : this.requests) {
			if (item.response(r).isPresent()) {
				matched = false;
				break;
			}
		}
		return matched;
	}
}
