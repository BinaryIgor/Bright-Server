package com.iprogrammerr.bright.server.respondent;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public final class ConditionalRespondentThatCanRespond extends TypeSafeMatcher<ConditionalRespondent> {

	private final List<Request> requests;
	private final Response response;

	public ConditionalRespondentThatCanRespond(List<Request> requests, Response response) {
		this.requests = requests;
		this.response = response;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(ConditionalRespondent item) {
		boolean matched;
		try {
			matched = true;
			for (Request r : this.requests) {
				matched = item.areConditionsMet(r) && item.response(r).equals(this.response);
				if (!matched) {
					break;
				}
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
