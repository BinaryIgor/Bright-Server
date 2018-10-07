package com.iprogrammerr.bright.server.pattern;

public final class AsteriskFilterUrlPattern implements FilterUrlPattern {

    private static final String MATCH_ALL = "*";
    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private final String urlPattern;

    public AsteriskFilterUrlPattern(String urlPattern) {
	this.urlPattern = urlPattern;
    }

    @Override
    public boolean isPrimary() {
	return urlPattern.startsWith(MATCH_ALL);
    }

    @Override
    public boolean isMatched(String url) {
	if (this.urlPattern.startsWith(MATCH_ALL)) {
	    return true;
	}
	String[] urlSegments = withoutParameters(url).split(URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = this.urlPattern.split(URL_SEGMENTS_SEPARATOR);
	boolean matched = true;
	if (this.urlPattern.endsWith(URL_SEGMENTS_SEPARATOR) && urlSegments.length >= urlPatternSegments.length) {
	    for (int i = 0; i < urlPatternSegments.length; i++) {
		if (!urlPatternSegments[i].equals(urlSegments[i])) {
		    matched = false;
		    break;
		}
	    }
	} else if (urlSegments.length != urlPatternSegments.length) {
	    matched = false;
	} else {
	    for (int i = 0; i < urlPatternSegments.length; i++) {
		if (!urlPatternSegments[i].equals(MATCH_ALL) && !urlPatternSegments[i].equals(urlSegments[i])) {
		    matched = false;
		    break;
		}
	    }
	}
	return matched;
    }

    private String withoutParameters(String url) {
	int questionMark = url.indexOf("?");
	return questionMark > 0 ? url.substring(0, questionMark) : url;
    }

}
