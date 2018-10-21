package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.model.TypedMap;

public final class TypedUrlPatternTest {

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
	TypedMap pathVariables = tup.pathVariables(url);
	assertTrue(pathVariables.has("id", Integer.class));
	int id = pathVariables.intValue("id");
	assertTrue(id == 1);
	assertTrue(pathVariables.has("scale", Double.class));
	double scale = pathVariables.doubleValue("scale");
	assertTrue(scale == 9.4);
    }

    @Test
    public void canReadParameters() throws Exception {
	String url = "riddle/user?id=1&search=10.33&fast=true&super=dada";
	TypedUrlPattern tup = new TypedUrlPattern("riddle/user?id=long&search=float&fast=boolean");
	TypedMap parameters = tup.parameters(url);
	assertTrue(parameters.has("id", Long.class));
	long id = parameters.longValue("id");
	assertTrue(id == 1);
	assertTrue(parameters.has("search", Float.class));
	float search = parameters.floatValue("search");
	assertTrue(10.33f == search);
	assertTrue(parameters.has("fast", Boolean.class));
	boolean fast = parameters.booleanValue("fast");
	assertTrue(fast);
    }

    @Test
    public void canReadNotRegisteredParameters() throws Exception {
	String url = "riddle/user?id=1&search=10.33&fast=true&super_search=4.56&comment=amazing";
	TypedUrlPattern tup = new TypedUrlPattern("riddle/user?id=long&search=double");
	TypedMap parameters = tup.parameters(url);
	assertTrue(parameters.has("id", Long.class));
	assertTrue(parameters.longValue("id") == 1L);
	assertTrue(parameters.has("search", Double.class));
	assertTrue(parameters.doubleValue("search") == 10.33);
	assertTrue(parameters.has("fast", Boolean.class));
	assertTrue(parameters.booleanValue("fast"));
	assertTrue(parameters.has("super_search", Double.class));
	assertTrue(parameters.doubleValue("super_search") == 4.56);
	assertTrue(parameters.has("comment", String.class));
	assertTrue(parameters.stringValue("comment").equals("amazing"));
    }

}
