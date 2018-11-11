package com.iprogrammerr.bright.server.pattern;

import java.util.Collections;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class UrlPatternThatCanMatchOrRefuse extends TypeSafeMatcher<UrlPattern> {

	private final List<String> urls;
	private final boolean match;

	public UrlPatternThatCanMatchOrRefuse(List<String> urls, boolean match) {
		this.urls = urls;
		this.match = match;
	}

	public UrlPatternThatCanMatchOrRefuse(String url, boolean match) {
		this(Collections.singletonList(url), match);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(UrlPattern item) {
		boolean matched = true;
		for (String url : this.urls) {
			matched = (this.match && item.isMatched(url)) || (!this.match && !item.isMatched(url));
			if (!matched) {
				break;
			}
		}
		return matched;
	}
}
