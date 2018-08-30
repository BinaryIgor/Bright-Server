package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.iprogrammerr.bright.server.parser.FilterUrlPatternParser;

public class FilterUrlPatternParserTest {

    private FilterUrlPatternParser urlPatternParser;

    @Before
    public void setup() {
	urlPatternParser = new FilterUrlPatternParser();
    }

    @Test
    public void properMatchTest() {
	urlPatternParser = new FilterUrlPatternParser();
	String url = "user/search/1/name";
	String urlPattern = "user/";
	assertTrue(urlPatternParser.match(url, urlPattern));
	urlPattern = "*";
	assertTrue(urlPatternParser.match(url, urlPattern));
	urlPattern = "user/search/*/*";
	assertTrue(urlPatternParser.match(url, urlPattern));
    }

    @Test
    public void imProperMatchTest() {
	urlPatternParser = new FilterUrlPatternParser();
	String url = "user/search/1/name";
	String urlPattern = "search/";
	assertFalse(urlPatternParser.match(url, urlPattern));
	urlPattern = "user/*";
	assertFalse(urlPatternParser.match(url, urlPattern));

    }
}
