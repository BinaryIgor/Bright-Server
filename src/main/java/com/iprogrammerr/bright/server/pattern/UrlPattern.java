package com.iprogrammerr.bright.server.pattern;

import com.iprogrammerr.bright.server.model.Pairs;

public interface UrlPattern {

    boolean match(String url);
    
    Pairs readPathVariables(String url);
    
    Pairs readParameters(String url);
    
    boolean hasParameters();
    
    boolean hasPathVariables();
}
