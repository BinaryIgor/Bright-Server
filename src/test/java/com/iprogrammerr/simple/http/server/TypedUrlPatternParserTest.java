package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.iprogrammerr.bright.server.model.Pairs;
import com.iprogrammerr.bright.server.parser.TypedUrlPatternParser;

public class TypedUrlPatternParserTest {

    private static final double DELTA = 10e-6;
    private TypedUrlPatternParser urlPatternParser;

    @Before
    public void setup() {
	urlPatternParser = new TypedUrlPatternParser();
    }


    @Test
    public void matchTest() {
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
	url = "ridde/simple/user/1";
	assertFalse(urlPatternParser.match(url, urlPattern));
	url = "complex/1/search?message=secret&scale=5.5";
	urlPattern = "complex/{id:long}/search?message=string&scale=float";
	assertTrue(urlPatternParser.match(url, urlPattern));
    }


    @Test
    public void readPathVariablesTest() {
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
    
    @Test
    public void readParametersTest() {
	String url = "riddle/user?id=1&search=10.33&fast=true&super=dada";
	String urlPattern = "riddle/user?id=long&search=float&fast=boolean";
	Pairs parameters = urlPatternParser.readParameters(url, urlPattern);
	assertTrue(parameters.has("id", Long.class));
	long id = parameters.get("id", Long.class);
	assertTrue(id == 1);
	assertTrue(parameters.has("search", Float.class));
	float search = parameters.get("search", Float.class); 
	assertTrue(Math.abs(10.33 - search) < DELTA);
	assertTrue(parameters.has("fast", Boolean.class));
	boolean fast = parameters.get("fast", Boolean.class);
	assertTrue(fast);
    }
}
