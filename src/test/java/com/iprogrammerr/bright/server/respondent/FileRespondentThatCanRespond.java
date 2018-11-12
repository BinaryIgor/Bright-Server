package com.iprogrammerr.bright.server.respondent;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.response.Response;

public final class FileRespondentThatCanRespond extends TypeSafeMatcher<FileRespondent> {

	private final TypedBinary source;
	private final Response response;

	public FileRespondentThatCanRespond(TypedBinary source, Response response) {
		this.source = source;
		this.response = response;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(FileRespondent item, Description description) {
		description.appendText(String.format("%s that does not give %s response to %s source",
				getClass().getSimpleName(), this.response, this.source));
	}

	@Override
	protected boolean matchesSafely(FileRespondent item) {
		boolean matched;
		try {
			matched = areResponsesMatched(item.response(this.source));
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}

	private boolean areResponsesMatched(Response response) {
		boolean matched = this.response.code() == response.code();
		if (matched) {
			matched = Arrays.equals(response.body(), this.response.body());
		}
		if (matched) {
			for (Header h : this.response.headers()) {
				matched = response.headers().contains(h);
				if (!matched) {
					break;
				}
			}
		}
		return matched;
	}
}
