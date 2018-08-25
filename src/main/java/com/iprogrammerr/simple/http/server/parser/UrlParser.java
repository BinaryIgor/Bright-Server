package com.iprogrammerr.simple.http.server.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iprogrammerr.simple.http.server.constants.ParserConstants;
import com.iprogrammerr.simple.http.server.exception.CreationException;
import com.iprogrammerr.simple.http.server.model.Parameter;
import com.iprogrammerr.simple.http.server.model.Parameters;
import com.iprogrammerr.simple.http.server.model.PathVariable;

public class UrlParser {

    private static final double IS_INTEGER_DELTA = 10e6;
    private static UrlParser instance;

    private UrlParser() {

    }

    public static UrlParser getInstance() {
	if (instance == null) {
	    instance = new UrlParser();
	}
	return instance;
    }

    public Map<String, Class> getRequiredUrlPathVariables(String urlPattern) {
	Map<String, String> keyTypeVariables = getKeyTypeVariablesFromUrlPattern(urlPattern);
	if (keyTypeVariables.isEmpty()) {
	    return new HashMap<>();
	}
	return rawToType(keyTypeVariables);
    }

    private Map<String, String> getKeyTypeVariablesFromUrlPattern(String urlPattern) {
	Map<String, String> keyTypeUrlPatternVariables = new HashMap<>();
	String[] urlPatternSegments = urlPattern.split(ParserConstants.URL_SEGMENTS_SEPARATOR);
	for (int i = 0; i < urlPatternSegments.length; i++) {
	    if (!isPathVariable(urlPatternSegments[i])) {
		continue;
	    }
	    String pathVariable = getRawPathVariable(urlPatternSegments[i]);
	    String[] keyAndType = pathVariable.split(ParserConstants.URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	    if (keyAndType.length >= 2) {
		keyTypeUrlPatternVariables.put(keyAndType[0], keyAndType[1]);
	    }
	}
	return keyTypeUrlPatternVariables;
    }

    public Map<String, String> getRawPathVariables(String url, String urlPattern) {
	Map<String, String> pathVariables = new HashMap<>();
	String[] urlSegments = url.split(ParserConstants.URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = urlPattern.split(ParserConstants.URL_SEGMENTS_SEPARATOR);
	if (urlSegments.length < urlPatternSegments.length) {
	    return pathVariables;
	}
	for (int i = 0; i < urlPatternSegments.length; i++) {
	    if (!isPathVariable(urlPatternSegments[i])) {
		continue;
	    }
	    pathVariables.put(getRawPathVariable(urlPatternSegments[i]), urlSegments[i]);
	}
	return pathVariables;
    }

    public List<PathVariable> getPathVariables(String url, String urlPattern) {
	List<PathVariable> pathVariables = new ArrayList<>();
	String[] urlSegments = url.split(ParserConstants.URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = urlPattern.split(ParserConstants.URL_SEGMENTS_SEPARATOR);
	if (urlSegments.length < urlPatternSegments.length) {
	    return pathVariables;
	}
	try {
	    for (int i = 0; i < urlPatternSegments.length; i++) {
		if (!isPathVariable(urlPatternSegments[i])) {
		    continue;
		}
		pathVariables.add(getPathVariable(urlSegments[i], urlPatternSegments[i]));
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	    pathVariables.clear();
	}
	return pathVariables;
    }

    private PathVariable getPathVariable(String urlSegment, String urlPatternSegment) {
	String rawPathVariable = getRawPathVariable(urlPatternSegment);
	if (rawPathVariable.isEmpty()) {
	    throw new CreationException();
	}
	String[] keyAndType = getRawPathVariable(urlPatternSegment)
		.split(ParserConstants.URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	System.out.println("urlPatternSegment = " + urlPatternSegment);
	if (keyAndType.length < 2) {
	    throw new CreationException();
	}
	Type variableType = Type.createFromString(keyAndType[1]);
	if (variableType.equals(Type.BOOLEAN)) {
	    return new PathVariable(keyAndType[0], Boolean.parseBoolean(urlSegment));
	}
	if (variableType.equals(Type.INT)) {
	    return new PathVariable(keyAndType[0], Integer.parseInt(urlSegment));
	}
	if (variableType.equals(Type.DOUBLE)) {
	    return new PathVariable(keyAndType[0], Double.parseDouble(urlSegment));
	}
	return new PathVariable(keyAndType[0], urlSegment);
    }

    private boolean isPathVariable(String urlPatternSegment) {
	return urlPatternSegment.startsWith(ParserConstants.URL_PATTERN_PATH_VARIABLE_START)
		&& urlPatternSegment.endsWith(ParserConstants.URL_PATTERN_PATH_VARIABLE_END);
    }

    private String getRawPathVariable(String urlPatternSegment) {
	return urlPatternSegment.substring(1, urlPatternSegment.length() - 1);
    }

    public boolean areEqual(String url, String urlPattern) {
	System.out.println("UrlParser.areEqual()");
	urlPattern = getWithoutParametersUrl(urlPattern);
	String[] urlSegments = url.split(ParserConstants.URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = urlPattern.split(ParserConstants.URL_SEGMENTS_SEPARATOR);
	if (urlSegments.length != urlPatternSegments.length) {
	    return false;
	}
	for (int i = 0; i < urlSegments.length; i++) {
	    if (isPathVariable(urlPatternSegments[i])) {
		continue;
	    }
	    if (!urlSegments[i].equals(urlPatternSegments[i])) {
		System.out.println("Not equal!" + urlSegments[i] + " vs " + urlPatternSegments[i]);
		return false;
	    }
	}
	return true;
    }

    private boolean isNumeric(String string) {
	try {
	    Double.parseDouble(string);
	    return true;
	} catch (Exception exception) {
	    return false;
	}
    }

    public Map<String, Class> getRequiredUrlParameters(String urlPattern) {
	Map<String, String> rawParameters = getRawParameters(urlPattern);
	if (rawParameters.isEmpty()) {
	    return new HashMap<>();
	}
	return rawToType(rawParameters);
    }

    private Map<String, Class> rawToType(Map<String, String> rawMap) {
	Map<String, Class> typedMap = new HashMap<>();
	try {
	    for (Map.Entry<String, String> rawEntry : rawMap.entrySet()) {
		Type variableType = Type.createFromString(rawEntry.getValue());
		typedMap.put(rawEntry.getKey(), variableType.getType());
	    }
	} catch (CreationException exception) {
	    exception.printStackTrace();
	    typedMap.clear();
	}
	return typedMap;
    }

    private Object getParameterValue(String parameter) {
	if (!isNumeric(parameter)) {
	    return parameter;
	}
	double numericParameter = Double.parseDouble(parameter);
	double delta = numericParameter - Math.floor(numericParameter);
	System.out.println("Flooring delta = " + delta);
	if (delta < IS_INTEGER_DELTA) {
	    return numericParameter;
	}
	return (int) numericParameter;
    }

    private Map<String, String> getRawParameters(String path) {
	Map<String, String> parameters = new HashMap<>();
	int indexOfQuestionMark = path.indexOf(ParserConstants.PARAMETERS_BEGINING) + 1;
	String toSplitParameters = path.substring(indexOfQuestionMark, path.length());
	String[] parametersKeyValuePairs = toSplitParameters.split(ParserConstants.PARAMETERS_SEPARATOR);
	if (parametersKeyValuePairs.length < 1) {
	    return parameters;
	}
	for (String keyValue : parametersKeyValuePairs) {
	    String[] keyAndValue = keyValue.split(ParserConstants.PARAMETERS_KEY_VALUE_SEPARATOR);
	    if (keyAndValue.length >= 2) {
		parameters.put(keyAndValue[0], keyAndValue[1]);
	    }
	}
	return parameters;
    }

    public Parameters getParameters(String path) {
	List<Parameter> parameters = new ArrayList<>();
	Map<String, String> rawParameters = getRawParameters(path);
	if (rawParameters.isEmpty()) {
	    return new Parameters(parameters);
	}
	for (Map.Entry<String, String> rawParameter : rawParameters.entrySet()) {
	    parameters.add(new Parameter(rawParameter.getKey(), getParameterValue(rawParameter.getValue())));
	}
	return new Parameters(parameters);
    }

    public String getWithoutParametersUrl(String url) {
	int indexOfParametersBegining = url.indexOf(ParserConstants.PARAMETERS_BEGINING);
	if (indexOfParametersBegining < 0) {
	    return url;
	}
	return url.substring(0, indexOfParametersBegining);
    }

}
