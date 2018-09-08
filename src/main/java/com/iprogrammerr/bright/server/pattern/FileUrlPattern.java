package com.iprogrammerr.bright.server.pattern;

import com.iprogrammerr.bright.server.model.KeysValues;
import com.iprogrammerr.bright.server.model.StringsObjects;

public class FileUrlPattern implements UrlPattern {

    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private static final String DOT = ".";

    @Override
    public boolean match(String url) {
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
    public KeysValues readPathVariables(String url) {
	return new StringsObjects();
    }

    @Override
    public KeysValues readParameters(String url) {
	return new StringsObjects();
    }

    @Override
    public boolean hasParameters() {
	return false;
    }

    @Override
    public boolean hasPathVariables() {
	return false;
    }

}
