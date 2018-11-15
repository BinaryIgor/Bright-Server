package com.iprogrammerr.bright.server.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.header.Header;

public final class ResponseThatHaveProperValues extends TypeSafeMatcher<Response> {

	private final int code;
	private final List<Header> headers;
	private final byte[] body;

	public ResponseThatHaveProperValues(int code, List<Header> headers, byte[] body) {
		this.code = code;
		this.headers = headers;
		this.body = body;
	}

	public ResponseThatHaveProperValues(int code, List<Header> headers) {
		this(code, headers, new byte[0]);
	}

	public ResponseThatHaveProperValues(int code) {
		this(code, new ArrayList<>());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Response item) {
		boolean matched = this.code == item.code() && Arrays.equals(this.body, item.body());
		if (matched) {
			for (Header h : this.headers) {
				matched = item.headers().contains(h);
				if (!matched) {
					break;
				}
			}
		}
		return matched;
	}
}
