package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.model.Attributes;

public final class TypedUrlPatternTest {

	@Test
	public void canMatchComplex() {
		assertThat(new TypedUrlPattern("riddle/user/{id:int}/search/{results:long}?scale=double"),
				new UrlPatternThatCanMatchOrRefuse(Arrays.asList("riddle/user/1/search/10?scale=3",
						"riddle/user/44/search/22?scale=5"), true));
	}

	@Test
	public void canMatchSimple() {
		assertThat(new TypedUrlPattern("riddle/user"),
				new UrlPatternThatCanMatchOrRefuse("riddle/user", true));
	}

	@Test
	public void canRefuseMatches() {
		assertThat(new TypedUrlPattern("riddle/user/{id:int}/search/{results:long}?scale=double"),
				new UrlPatternThatCanMatchOrRefuse(Arrays.asList("riddle/user/1/search/10",
						"riddle/user/search/22?scale=5", "riddle/user/1"), false));
	}

	@Test
	public void canReadPathVariables() throws Exception {
		assertThat(new TypedUrlPattern("riddle/user/{id:int}/search/{scale:double}"),
				new ParameterizableUrlPatternThatCanReadPathVariablesAndParameters(
						"riddle/user/1/search/9.4", new Attributes(),
						new Attributes().put("id", 1).put("scale", 9.4)));
	}

	@Test
	public void canReadParameters() throws Exception {
		assertThat(
				new TypedUrlPattern("riddle/user?id=long&search=float&fast=boolean&comment=string"),
				new ParameterizableUrlPatternThatCanReadPathVariablesAndParameters(
						"riddle/user?id=1&search=10.33&fast=true&comment=amazing",
						new Attributes().put("id", 1L).put("search", 10.33f).put("fast", true)
								.put("comment", "amazing"),
						new Attributes()));
	}

	@Test
	public void canReadAdditionalParameters() throws Exception {
		assertThat(new TypedUrlPattern("riddle/user/{id:long}/search?scale=double"),
				new ParameterizableUrlPatternThatCanReadPathVariablesAndParameters(
						"riddle/user/1/search?scale=10.33&fast=true&comment=amazing",
						new Attributes().put("scale", 10.33).put("fast", true).put("comment",
								"amazing"),
						new Attributes().put("id", 1L)));
	}

}
