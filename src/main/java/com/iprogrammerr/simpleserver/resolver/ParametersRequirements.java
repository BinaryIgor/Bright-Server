package com.iprogrammerr.simpleserver.resolver;

import java.util.Map;

import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.parser.UrlParser;

public class ParametersRequirements {

    private final Map<String, Class> requiredParameters;

    public ParametersRequirements(String urlPattern, UrlParser urlParser) {
	requiredParameters = urlParser.getRequiredUrlParameters(urlPattern);
    }

    public boolean validate(Request request) {
	for (Map.Entry<String, Class> neededParameter : requiredParameters.entrySet()) {
	    if (!request.hasParameter(neededParameter.getKey(), neededParameter.getValue())) {
		return false;
	    }
	}
	return true;
    }

    public boolean has() {
	return !requiredParameters.isEmpty();
    }
}
