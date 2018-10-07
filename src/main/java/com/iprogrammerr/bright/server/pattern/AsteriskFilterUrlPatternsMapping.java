package com.iprogrammerr.bright.server.pattern;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.model.Mapping;

public final class AsteriskFilterUrlPatternsMapping implements Mapping<String[], List<FilterUrlPattern>> {

    @Override
    public List<FilterUrlPattern> value(String[] from) {
	List<FilterUrlPattern> urlPatterns = new ArrayList<>();
	for (String up : from) {
	    urlPatterns.add(new AsteriskFilterUrlPattern(up));
	}
	return urlPatterns;
    }

}
