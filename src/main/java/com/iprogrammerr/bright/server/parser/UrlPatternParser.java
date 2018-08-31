package com.iprogrammerr.bright.server.parser;

import com.iprogrammerr.bright.server.model.Pairs;

public interface UrlPatternParser {

    boolean match(String url, String urlPattern);
    
    Pairs readPathVariables(String url, String urlPattern);
    
    Pairs readParameters(String url);
    
    boolean hasParameters(String urlPattern);
    
    boolean hasPathVariables(String urlPattern);
}
