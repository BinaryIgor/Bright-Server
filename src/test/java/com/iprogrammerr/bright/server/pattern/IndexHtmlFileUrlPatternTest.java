package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public final class IndexHtmlFileUrlPatternTest {

	private static final String CURRENT_PATH = new File(
			String.format("src%stest%sresources", File.separator, File.separator)).getAbsolutePath();

	@Test
	public void canMatch() {
		IndexHtmlFileUrlPattern pattern = new IndexHtmlFileUrlPattern(CURRENT_PATH);
		assertThat(pattern, new UrlPatternThatCanMatchOrRefuse(Arrays.asList("test.html", "index.html"), true));
	}

	@Test
	public void canReadFilePath() {
		IndexHtmlFileUrlPattern pattern = new IndexHtmlFileUrlPattern(CURRENT_PATH);
		Map<String, String> urlsToFilePaths = new HashMap<>();
		urlsToFilePaths.put("index.html", CURRENT_PATH + File.separator + "index.html");
		urlsToFilePaths.put("test.html", CURRENT_PATH + File.separator + "test.html");
		assertThat(pattern, new FileUrlPatternThatCanReadPaths(urlsToFilePaths));
	}
}
