package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.pattern.FileUrlPattern;
import com.iprogrammerr.bright.server.pattern.UrlPattern;

public class FileUrlPatternTest {

    @Test
    public void match() {
	UrlPattern urlPattern = new FileUrlPattern();
	String url = "";
	assertTrue(urlPattern.match(url));
    }
}
