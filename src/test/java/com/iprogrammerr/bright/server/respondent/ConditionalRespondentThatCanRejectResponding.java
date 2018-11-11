package com.iprogrammerr.bright.server.respondent;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.request.Request;

public final class ConditionalRespondentThatCanRejectResponding extends TypeSafeMatcher<ConditionalRespondent> {

	private final List<Request> requests;

	public ConditionalRespondentThatCanRejectResponding(List<Request> requests) {
		this.requests = requests;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(ConditionalRespondent item) {
		boolean matched;
		matched = true;
		for (Request r : this.requests) {
			try {
				matched = !item.areConditionsMet(r);
				if (matched) {
					item.response(r);
				}
				matched = false;
			} catch (Exception e) {
				matched = true;
			}
			if (!matched) {
				break;
			}
		}
		return matched;
	}
}
