package com.iprogrammerr.simpleserver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.iprogrammerr.simpleserver.model.PathVariable;
import com.iprogrammerr.simpleserver.parser.UrlParser;

public class UrlParserTest {

    private UrlParser urlParser;

    @Before
    public void setup() {
	urlParser = UrlParser.getInstance();
    }

    @Test
    public void rawParseTest() {
	String url = "riddle/user/1/search";
	String urlPattern = "riddle/user/{id}/search";
	Map<String, String> pathVariables = urlParser.getRawPathVariables(url, urlPattern);
	assertTrue(pathVariables.size() == 1);
	assertTrue(pathVariables.get("id") != null);
	url = "riddle/user/1/search/10";
	urlPattern = "riddle/user/{id}/search/{minResults}";
	pathVariables = urlParser.getRawPathVariables(url, urlPattern);
	assertTrue(pathVariables.size() == 2);
	assertTrue(pathVariables.get("id") != null);
	assertTrue(pathVariables.get("minResults") != null);
    }

    @Test
    public void areUrlAndUrlPatternEqualTest() {
	String url = "riddle/user/1/search/10";
	String urlPattern = "riddle/user/{id}/search/{results}";
	assertTrue(urlParser.areEqual(url, urlPattern));
	urlPattern += urlPattern + "/da";
	assertFalse(urlParser.areEqual(url, urlPattern));
	urlPattern = "riddle/user/{id}/search";
	assertFalse(urlParser.areEqual(url, urlPattern));
    }

    @Test
    public void getRequiredVariablesPathTest() {
	String urlPattern = "riddle/user/{id:int}/search";
	Map<String, Class> requiredPathVariables = urlParser.getRequiredUrlPathVariables(urlPattern);
	assertTrue(requiredPathVariables.size() == 1);
	urlPattern = "riddle/user/{id:intdd}/search";
	requiredPathVariables = urlParser.getRequiredUrlPathVariables(urlPattern);
	assertTrue(requiredPathVariables.isEmpty());
    }

    @Test
    public void getPathVariablesTest() {
	String url = "riddle/user/1/search/9.4";
	String urlPattern = "riddle/user/{id:int}/search/{scale:double}";
	List<PathVariable> pathVariables = urlParser.getPathVariables(url, urlPattern);
	assertTrue(pathVariables.size() == 2);
	int id = (int) pathVariables.get(0).getValue();
	assertTrue(id == 1);
	double scale = (double) pathVariables.get(1).getValue();
	assertTrue(scale == 9.4);
    }
}
