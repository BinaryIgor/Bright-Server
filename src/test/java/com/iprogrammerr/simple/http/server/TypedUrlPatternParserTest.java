package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.iprogrammerr.bright.server.model.Pairs;
import com.iprogrammerr.bright.server.parser.TypedUrlPatternParser;

public class TypedUrlPatternParserTest {

    private TypedUrlPatternParser urlPatternParser;

    @Before
    public void setup() {
	urlPatternParser = new TypedUrlPatternParser();
    }


    @Test
    public void areUrlAndUrlPatternEqualTest() {
	String url = "riddle/user/1/search/10";
	String urlPattern = "riddle/user/{id}/search/{results}";
	assertTrue(urlPatternParser.match(url, urlPattern));
	urlPattern += urlPattern + "/da";
	assertFalse(urlPatternParser.match(url, urlPattern));
	urlPattern = "riddle/user/{id}/search";
	assertFalse(urlPatternParser.match(url, urlPattern));
	url = "riddle/user/1";
	urlPattern ="riddle/user/{id:int}";
	assertTrue(urlPatternParser.match(url, urlPattern));
    }


    @Test
    public void getPathVariablesTest() {
	String url = "riddle/user/1/search/9.4";
	String urlPattern = "riddle/user/{id:int}/search/{scale:double}";
	Pairs pathVariables = urlPatternParser.readPathVariables(url, urlPattern);
	assertTrue(pathVariables.has("id", Integer.class));
	int id = pathVariables.get("id", Integer.class);
	assertTrue(id == 1);
	assertTrue(pathVariables.has("scale", Double.class));
	double scale = pathVariables.get("scale", Double.class);
	assertTrue(scale == 9.4);
    }
}
