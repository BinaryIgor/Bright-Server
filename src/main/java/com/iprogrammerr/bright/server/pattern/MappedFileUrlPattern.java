package com.iprogrammerr.bright.server.pattern;

import java.io.File;
import java.util.Map;

public final class MappedFileUrlPattern implements FileUrlPattern {

	private static final String SEGMENTS_SEPARATOR = "/";
	private static final String HIGHER_DIRECTORY = "..";
	private final String rootDirectory;
	private final FileUrlPattern base;
	private final Map<String, String> mappings;

	public MappedFileUrlPattern(String rootDirectory, FileUrlPattern base, Map<String, String> mappings) {
		this.rootDirectory = rootDirectory;
		this.base = base;
		this.mappings = mappings;
	}

	public MappedFileUrlPattern(String rootDirectory, Map<String, String> mappings) {
		this(rootDirectory, new IndexHtmlFileUrlPattern(rootDirectory), mappings);
	}

	@Override
	public boolean isMatched(String url) {
		boolean matched = this.base.isMatched(url);
		if (!matched) {
			url = withoutParameters(url);
			if (url.contains(HIGHER_DIRECTORY) || !this.mappings.containsKey(url)) {
				matched = false;
			} else {
				matched = new File(path(url)).exists();
			}
		}
		return matched;
	}

	@Override
	public String filePath(String url) {
		url = withoutParameters(url);
		String path;
		if (this.base.isMatched(url)) {
			path = this.base.filePath(url);
		} else if (this.mappings.containsKey(url)) {
			path = path(url);
		} else {
			path = url;
		}
		return path;
	}

	private String withoutParameters(String url) {
		int indexOfQuestionMark = url.indexOf("?");
		return indexOfQuestionMark > 0 ? url.substring(0, indexOfQuestionMark) : url;
	}

	private String path(String url) {
		return this.rootDirectory + File.separator
				+ String.join(File.separator, this.mappings.get(url).split(SEGMENTS_SEPARATOR));
	}
}
