package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.model.KeysValues;
import com.iprogrammerr.bright.server.pattern.TypedUrlPattern;

public class TypedUrlPatternTest {

    private static final double DELTA = 10e-6;
    private TypedUrlPattern urlPattern;

    @Test
    public void match() {
	String url = "riddle/user/1/search/10";
	urlPattern = new TypedUrlPattern("riddle/user/{id}/search/{results}");
	assertTrue(urlPattern.match(url));
	urlPattern = new TypedUrlPattern("riddle/user/{id}/search/{results}/dada");
	assertFalse(urlPattern.match(url));
	urlPattern = new TypedUrlPattern("riddle/user/{id}/search");
	assertFalse(urlPattern.match(url));
	url = "riddle/user/1";
	urlPattern = new TypedUrlPattern("riddle/user/{id:int}");
	assertTrue(urlPattern.match(url));
	url = "ridde/simple/user/1";
	assertFalse(urlPattern.match(url));
	url = "complex/1/search?message=secret&scale=5.5";
	urlPattern = new TypedUrlPattern("complex/{id:long}/search?message=string&scale=float");
	assertTrue(urlPattern.match(url));
    }

    @Test
    public void readPathVariables() throws Exception {
	String url = "riddle/user/1/search/9.4";
	urlPattern = new TypedUrlPattern("riddle/user/{id:int}/search/{scale:double}");
	KeysValues pathVariables = urlPattern.readPathVariables(url);
	assertTrue(pathVariables.has("id", Integer.class));
	int id = pathVariables.value("id", Integer.class);
	assertTrue(id == 1);
	assertTrue(pathVariables.has("scale", Double.class));
	double scale = pathVariables.value("scale", Double.class);
	assertTrue(scale == 9.4);
    }

    @Test
    public void readParameters() throws Exception {
	String url = "riddle/user?id=1&search=10.33&fast=true&super=dada";
	urlPattern = new TypedUrlPattern("riddle/user?id=long&search=float&fast=boolean");
	KeysValues parameters = urlPattern.readParameters(url);
	assertTrue(parameters.has("id", Long.class));
	long id = parameters.value("id", Long.class);
	assertTrue(id == 1);
	assertTrue(parameters.has("search", Float.class));
	float search = parameters.value("search", Float.class);
	assertTrue(Math.abs(10.33 - search) < DELTA);
	assertTrue(parameters.has("fast", Boolean.class));
	boolean fast = parameters.value("fast", Boolean.class);
	assertTrue(fast);
    }
}
