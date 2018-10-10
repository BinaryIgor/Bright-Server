package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.model.KeysValues;
import com.iprogrammerr.bright.server.pattern.TypedUrlPattern;

public class TypedUrlPatternTest {

    private static final double DELTA = 10e-6;

    @Test
    public void canMatch() {
	String url = "riddle/user/1/search/10";
	TypedUrlPattern tup = new TypedUrlPattern("riddle/user/{id}/search/{results}");
	assertTrue(tup.isMatched(url));
	url = "riddle/user/1";
	tup = new TypedUrlPattern("riddle/user/{id:int}");
	assertTrue(tup.isMatched(url));
	url = "complex/1/search?message=secret&scale=5.5";
	tup = new TypedUrlPattern("complex/{id:long}/search?message=string&scale=float");
	assertTrue(tup.isMatched(url));
    }

    @Test
    public void canRefuseMatches() {
	String url = "riddle/user/1/search/10";
	TypedUrlPattern tup = new TypedUrlPattern("riddle/user/{id}/search/{results}/dada");
	assertFalse(tup.isMatched(url));
	tup = new TypedUrlPattern("riddle/user/{id}/search");
	assertFalse(tup.isMatched(url));
	url = "ridde/simple/user/1";
	assertFalse(tup.isMatched(url));
    }

    @Test
    public void canReadPathVariables() throws Exception {
	String url = "riddle/user/1/search/9.4";
	TypedUrlPattern tup = new TypedUrlPattern("riddle/user/{id:int}/search/{scale:double}");
	KeysValues pathVariables = tup.pathVariables(url);
	assertTrue(pathVariables.has("id", Integer.class));
	int id = pathVariables.value("id", Integer.class);
	assertTrue(id == 1);
	assertTrue(pathVariables.has("scale", Double.class));
	double scale = pathVariables.value("scale", Double.class);
	assertTrue(scale == 9.4);
    }

    @Test
    public void canReadParameters() throws Exception {
	String url = "riddle/user?id=1&search=10.33&fast=true&super=dada";
	TypedUrlPattern tup = new TypedUrlPattern("riddle/user?id=long&search=float&fast=boolean");
	KeysValues parameters = tup.parameters(url);
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
