package com.iprogrammerr.bright.server;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.application.Application;
import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public final class ApplicationThatCanRespond extends TypeSafeMatcher<Application> {

	private final List<Request> requests;
	private final Response response;

	public ApplicationThatCanRespond(List<Request> requests, Response response) {
		this.requests = requests;
		this.response = response;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(Application item, Description description) {
		description.appendText(String.format("%s that does not give %s to %s", getClass().getSimpleName(),
				this.response, this.requests));
	}

	@Override
	protected boolean matchesSafely(Application item) {
		boolean matched;
		try {
			matched = true;
			for (Request r : this.requests) {
				matched = isResponseMatched(item.response(r).get());
				if (!matched) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			matched = false;
		}
		return matched;
	}

	private boolean isResponseMatched(Response response) {
		boolean equal = this.response.code() == response.code();
		if (equal) {
			for (Header h : this.response.headers()) {
				equal = response.headers().contains(h);
				if (!equal) {
					break;
				}
			}
		}
		if (equal) {
			equal = Arrays.equals(this.response.body(), response.body());
		}
		return equal;
	}
}
