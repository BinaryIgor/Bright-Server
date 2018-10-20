package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public final class IndexHtmlFileUrlPatternTest {

    private static final String CURRENT_PATH = new File(
	    String.format("src%stest%sresources", File.separator, File.separator)).getAbsolutePath();

    @Test
    public void canMatchAndRefuse() {
	IndexHtmlFileUrlPattern pattern = new IndexHtmlFileUrlPattern(CURRENT_PATH);
	assertTrue(pattern.isMatched("test.html"));
	assertTrue(pattern.isMatched(""));
	assertFalse(pattern.isMatched("test/"));
    }

    @Test
    public void canReadFilePath() {
	IndexHtmlFileUrlPattern pattern = new IndexHtmlFileUrlPattern(CURRENT_PATH);
	String expected = CURRENT_PATH + File.separator + "test.html";
	assertTrue(pattern.filePath("test.html").equals(expected));
	expected = CURRENT_PATH + File.separator + "index.html";
	assertTrue(pattern.filePath("").equals(expected));
    }

}
