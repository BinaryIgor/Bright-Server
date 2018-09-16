package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.pattern.FileUrlPattern;
import com.iprogrammerr.bright.server.pattern.IndexHtmlFileUrlPattern;

public class FileUrlPatternTest {

    @Test
    public void match() {
	FileUrlPattern urlPattern = new IndexHtmlFileUrlPattern();
	String url = "";
	assertTrue(urlPattern.match(url));
    }
}
