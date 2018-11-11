package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public final class AsteriskFilterUrlPatternTest {

	@Test
	public void canMatchPrimary() {
		AsteriskFilterUrlPattern urlPattern = new AsteriskFilterUrlPattern("*");
		assertThat(urlPattern, new UrlPatternThatCanMatchOrRefuse(
				Arrays.asList("user/*, user/search/1, user/search/*", "query/secret?factor=1"),
				true));
	}

	@Test
	public void canMatchStartingFrom() {
		AsteriskFilterUrlPattern urlPattern = new AsteriskFilterUrlPattern("user/");
		assertThat(urlPattern, new UrlPatternThatCanMatchOrRefuse(
				Arrays.asList("user/profile/*, user/search/1, user/search/*"), true));
	}

	@Test
	public void canMatchConcrete() {
		AsteriskFilterUrlPattern urlPattern = new AsteriskFilterUrlPattern("concrete");
		assertThat(urlPattern, new UrlPatternThatCanMatchOrRefuse("concrete", true));
	}

	@Test
	public void canMatchMixed() {
		AsteriskFilterUrlPattern urlPattern = new AsteriskFilterUrlPattern("domain/*/*");
		assertThat(urlPattern, new UrlPatternThatCanMatchOrRefuse(
				Arrays.asList("domain/a/1", "domain/b/4", "domain/c/number"), true));
	}

	@Test
	public void canRefuseMatches() {
		AsteriskFilterUrlPattern urlPattern = new AsteriskFilterUrlPattern("search/*");
		assertThat(urlPattern, new UrlPatternThatCanMatchOrRefuse(
				Arrays.asList("domain1/a", "domain2/b", "domain4"), false));
	}
}
