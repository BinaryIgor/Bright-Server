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
	    matched = segments.length == 0 || segments[0].isEmpty() ? true
		    : new File(this.rootDirectory + File.separator + withoutParameters(url)).exists();
	}
	return matched;
    }

    @Override
    public String filePath(String url) {
	String path = withoutParameters(url);
	if (path.isEmpty()) {
	    path = this.rootDirectory + File.separator + "index.html";
	} else if (path.endsWith(SEGMENTS_SEPARATOR)) {
	    path += "index.html";
	} else {
	    path = this.rootDirectory + File.separator + path.replace(SEGMENTS_SEPARATOR, File.separator);
	}
	return path;
    }

    private String withoutParameters(String url) {
	int indexOfQuestionMark = url.indexOf("?");
	return indexOfQuestionMark > 0 ? url.substring(0, indexOfQuestionMark) : url;
    }

}
