package com.iprogrammerr.bright.server.cors;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.request.Request;

public final class PreflightCorsThatCanRecognizeAndValidateRequests extends TypeSafeMatcher<PreflightCors> {

	private final List<Request> corsRequests;
	private final List<Request> nonCorsRequests;

	public PreflightCorsThatCanRecognizeAndValidateRequests(List<Request> corsRequests, List<Request> nonCorsRequests) {
		this.corsRequests = corsRequests;
		this.nonCorsRequests = nonCorsRequests;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(PreflightCors item, Description description) {
		description.appendText(String.format("%s that can not recognize %s as being cors and %s as not",
				getClass().getSimpleName(), this.corsRequests, this.nonCorsRequests));
	}

	@Override
	protected boolean matchesSafely(PreflightCors item) {
		boolean matched = true;
		for (Request r : this.corsRequests) {
			matched = item.is(r) && item.isValid(r);
			if (!matched) {
				break;
			}
		}
		if (matched) {
			for (Request r : this.nonCorsRequests) {
				matched = !item.is(r) && !item.isValid(r);
				if (!matched) {
					break;
				}
			}
		}
		return matched;
	}
}
