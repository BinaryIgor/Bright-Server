package com.iprogrammerr.bright.server.pattern;

import java.io.File;

public class IndexHtmlFileUrlPattern implements FileUrlPattern {

    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private static final String GO_TO_HIGHER_DIRECTORY = "..";
    private final String rootDirectory;

    public IndexHtmlFileUrlPattern(String rootDirectory) {
	this.rootDirectory = rootDirectory;
    }

    @Override
    public boolean match(String url) {
	if (url.contains(GO_TO_HIGHER_DIRECTORY)) {
	    return false;
	}
	String[] segments = url.split(URL_SEGMENTS_SEPARATOR);
	if (segments.length == 0 || segments[0].isEmpty()) {
	    return true;
	}
	String path = rootDirectory + File.separator + cutParameters(url);
	return new File(path).exists();
    }

    @Override
    public String filePath(String url) {
	String filePath = cutParameters(url);
	if (filePath.isEmpty()) {
	    return rootDirectory + File.separator + "index.html";
	}
	if (filePath.endsWith(URL_SEGMENTS_SEPARATOR)) {
	    filePath += "index.html";
	}
	return rootDirectory + File.separator + filePath.replace(URL_SEGMENTS_SEPARATOR, File.separator);
    }

    private String cutParameters(String url) {
	int indexOfQuestionMark = url.indexOf("?");
	return indexOfQuestionMark > 0 ? url.substring(0, indexOfQuestionMark) : url;
    }

}
