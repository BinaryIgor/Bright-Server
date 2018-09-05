package com.iprogrammerr.bright.server.pattern;

public class StarSymbolFilterUrlPattern implements ToFilterUrlPattern{

    private static final String MATCH_ALL = "*";
    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private String urlPattern;
    
    public StarSymbolFilterUrlPattern(String urlPattern) {
	this.urlPattern = urlPattern;
    }

    @Override
    public boolean isPrimary() {
	return urlPattern.startsWith(MATCH_ALL);
    }

    @Override
    public boolean match(String url) {
	if (urlPattern.startsWith(MATCH_ALL)) {
	    return true;
	}
	String[] urlSegments = url.split(URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = urlPattern.split(URL_SEGMENTS_SEPARATOR);
	if (urlPattern.endsWith(URL_SEGMENTS_SEPARATOR)) {
	    for (int i = 0; i < urlPatternSegments.length; i++) {
		if (!urlPatternSegments[i].equals(urlSegments[i])) {
		    return false;
		}
	    }
	    return true;
	}
	if (urlSegments.length != urlPatternSegments.length) {
	    return false;
	}
	for (int i = 0; i < urlPatternSegments.length; i++) {
	    if (!urlPatternSegments[i].equals(MATCH_ALL) && !urlPatternSegments[i].equals(urlSegments[i])) {
		return false;
	    }
	}
	return true;
    }
}
