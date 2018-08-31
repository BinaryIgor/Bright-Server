package com.iprogrammerr.bright.server.parser;

public class StarSymbolFilterUrlPatternParser implements FilterUrlPatternParser{

    private static final String MATCH_ALL = "*";
    private static final String URL_SEGMENTS_SEPARATOR = "/";

    @Override
    public boolean isPrimary(String urlPattern) {
	return urlPattern.startsWith(MATCH_ALL);
    }

    @Override
    public boolean match(String url, String urlPattern) {
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
