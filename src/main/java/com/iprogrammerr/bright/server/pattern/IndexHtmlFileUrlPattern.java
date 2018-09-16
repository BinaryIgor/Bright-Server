package com.iprogrammerr.bright.server.pattern;

import java.io.File;

public class IndexHtmlFileUrlPattern implements FileUrlPattern {

    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private static final String DOT = ".";

    @Override
    public boolean match(String url) {
	if (url.endsWith(URL_SEGMENTS_SEPARATOR)) {
	    return true;
	}
	String[] segments = url.split(URL_SEGMENTS_SEPARATOR);
	if (segments.length == 0 || segments[0].isEmpty()) {
	    return true;
	}
	String potentialFileName = segments[segments.length - 1];
	int indexOfDot = potentialFileName.indexOf(DOT);
	if (indexOfDot < 1) {
	    return false;
	}
	return potentialFileName.substring(indexOfDot, potentialFileName.length()).length() > 0;
    }

    @Override
    public String filePath(String url) {
	int indexOfQuestionMark = url.indexOf("?");
	String filePath = indexOfQuestionMark > 0 ? url.substring(0, indexOfQuestionMark) : url;
	if (filePath.isEmpty()) {
	    return "index.html";
	}
	if (filePath.endsWith(URL_SEGMENTS_SEPARATOR)) {
	    filePath += "index.html";
	}
	return filePath.replace(URL_SEGMENTS_SEPARATOR, File.separator);
    }

}
