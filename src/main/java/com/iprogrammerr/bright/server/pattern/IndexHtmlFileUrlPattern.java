package com.iprogrammerr.bright.server.pattern;

import java.io.File;

public final class IndexHtmlFileUrlPattern implements FileUrlPattern {

	private static final String SEGMENTS_SEPARATOR = "/";
	private static final String HIGHER_DIRECTORY = "..";
	private final String rootDirectory;

	public IndexHtmlFileUrlPattern(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	@Override
	public boolean isMatched(String url) {
		boolean matched;
		if (url.contains(HIGHER_DIRECTORY)) {
			matched = false;
		} else {
			String[] segments = url.split(SEGMENTS_SEPARATOR);
			matched = (segments.length == 0 || segments[0].isEmpty()) ? true
					: new File(path(withoutParameters(url))).exists();
		}
		return matched;
	}

	@Override
	public String filePath(String url) {
		url = withoutParameters(url);
		String path;
		if (url.isEmpty()) {
			path = this.rootDirectory + File.separator + "index.html";
		} else if (url.endsWith(SEGMENTS_SEPARATOR)) {
			path = path(url) + "index.html";
		} else {
			path = path(url);
		}
		return path;
	}

	private String withoutParameters(String url) {
		int indexOfQuestionMark = url.indexOf("?");
		return indexOfQuestionMark > 0 ? url.substring(0, indexOfQuestionMark) : url;
	}

	private String path(String url) {
		return String.format("%s%s%s", this.rootDirectory, File.separator,
				url.replace(SEGMENTS_SEPARATOR, File.separator));
	}
}
