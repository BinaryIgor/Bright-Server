package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AsteriskFilterUrlPatternTest {

    @Test
    public void canMatch() {
	AsteriskFilterUrlPattern urlPattern = new AsteriskFilterUrlPattern("user/");
	String url = "user/search/1/name";
	assertTrue(urlPattern.isMatched(url));
	urlPattern = new AsteriskFilterUrlPattern("*");
	assertTrue(urlPattern.isMatched(url));
	urlPattern = new AsteriskFilterUrlPattern("user/search/*/*");
	assertTrue(urlPattern.isMatched(url));
    }

    @Test
    public void canRefuseMatches() {
	AsteriskFilterUrlPattern urlPattern = new AsteriskFilterUrlPattern("search/");
	String url = "user/search/1/name";
	assertFalse(urlPattern.isMatched(url));
	urlPattern = new AsteriskFilterUrlPattern("user/*");
	assertFalse(urlPattern.isMatched(url));

    }
}
