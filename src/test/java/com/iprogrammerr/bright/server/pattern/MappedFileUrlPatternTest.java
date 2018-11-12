package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public final class MappedFileUrlPatternTest {

	private static final String CURRENT_PATH = new File(
			String.format("src%stest%sresources", File.separator, File.separator)).getAbsolutePath();

	@Test
	public void canMatch() {
		Map<String, String> mappings = new HashMap<>();
		mappings.put("secret/opening", "index.html");
		mappings.put("secret/test", "test.html");
		FileUrlPattern pattern = new MappedFileUrlPattern(CURRENT_PATH, mappings);
		assertThat(pattern,
				new UrlPatternThatCanMatchOrRefuse(Arrays.asList("secret/opening", "secret/test", "index.html"), true));
	}

	@Test
	public void canReadFilePath() {
		Map<String, String> mappings = new HashMap<>();
		mappings.put("secret/opening", "index.html");
		mappings.put("secret/secret2/test", "test.html");
		FileUrlPattern pattern = new MappedFileUrlPattern(CURRENT_PATH, mappings);
		Map<String, String> urlsToFilePaths = new HashMap<>();
		urlsToFilePaths.put("secret/opening", CURRENT_PATH + File.separator + "index.html");
		urlsToFilePaths.put("secret/secret2/test", CURRENT_PATH + File.separator + "test.html");
		urlsToFilePaths.put("test.html", CURRENT_PATH + File.separator + "test.html");
		assertThat(pattern, new FileUrlPatternThatCanReadPaths(urlsToFilePaths));
	}
}
