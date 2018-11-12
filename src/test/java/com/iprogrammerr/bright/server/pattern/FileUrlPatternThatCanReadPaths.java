package com.iprogrammerr.bright.server.pattern;

import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class FileUrlPatternThatCanReadPaths extends TypeSafeMatcher<FileUrlPattern> {

	private final Map<String, String> urlsToFilePaths;

	public FileUrlPatternThatCanReadPaths(Map<String, String> urlsToFilePaths) {
		this.urlsToFilePaths = urlsToFilePaths;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(
				String.format("%s that should match on map %s", getClass().getSimpleName(), this.urlsToFilePaths));
	}

	@Override
	protected boolean matchesSafely(FileUrlPattern item) {
		boolean matched = true;
		for (Map.Entry<String, String> entry : this.urlsToFilePaths.entrySet()) {
			if (!item.filePath(entry.getKey()).equals(entry.getValue())) {
				matched = false;
				break;
			}
		}
		return matched;
	}
}
