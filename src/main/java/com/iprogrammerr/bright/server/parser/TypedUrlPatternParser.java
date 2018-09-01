package com.iprogrammerr.bright.server.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iprogrammerr.bright.server.exception.CreationException;
import com.iprogrammerr.bright.server.model.Pair;
import com.iprogrammerr.bright.server.model.Pairs;
import com.iprogrammerr.bright.server.model.UrlPatternType;

public class TypedUrlPatternParser implements UrlPatternParser {

    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private static final String PARAMETERS_BEGINING = "?";
    private static final String PARAMETERS_SEPARATOR = "&";
    private static final String PARAMETERS_KEY_VALUE_SEPARATOR = "=";
    private static final String URL_PATTERN_PATH_VARIABLE_START = "{";
    private static final String URL_PATTERN_PATH_VARIABLE_END = "}";
    private static final String URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR = ":";

    public boolean match(String url, String urlPattern) {
	Map<String, Class> requiredUrlPathVariables = readRequiredUrlPathVariables(urlPattern);
	boolean haveRequiredUrlPathVariables = requiredUrlPathVariables.isEmpty() 
		 || checkVariables(requiredUrlPathVariables, readPathVariables(url, urlPattern));
	if (!haveRequiredUrlPathVariables) {
	    return false;
	}
	Map<String, Class> requiredUrlParameters = readRequiredUrlParameters(urlPattern);
	boolean haveRequiredUrlParameters = requiredUrlParameters.isEmpty()
		|| checkVariables(requiredUrlParameters, readParameters(url, urlPattern));
	if (!haveRequiredUrlParameters) {
	    return false;
	}
	return matchOmittingPathVariablesAndParameters(url, urlPattern);
    }

    private boolean checkVariables( Map<String, Class> requiredVariables, Pairs variables) {
	for (Map.Entry<String, Class> requiredVariable : requiredVariables.entrySet()) {
	    if (!variables.has(requiredVariable.getKey(), requiredVariable.getValue())) {
		return false;
	    }
	}
	return true;
    }

    private boolean matchOmittingPathVariablesAndParameters(String url, String urlPattern) {
	urlPattern = cutParameters(urlPattern);
	url = cutParameters(url);
	String[] urlPatternSegments = urlPattern.split(URL_SEGMENTS_SEPARATOR);
	String[] urlSegments = url.split(URL_SEGMENTS_SEPARATOR);
	if (urlPatternSegments.length != urlSegments.length) {
	    return false;
	}
	for (int i = 0; i < urlPatternSegments.length; i++) {
	    if (isPathVariable(urlPatternSegments[i])) {
		continue;
	    }
	    if (!urlPatternSegments[i].equals(urlSegments[i])) {
		return false;
	    }
	}
	return true;
    }

    private String cutParameters(String url) {
	int indexOfParametersBegining = url.indexOf(PARAMETERS_BEGINING);
	if (indexOfParametersBegining >= 0) {
	    return url.substring(0, indexOfParametersBegining);
	}
	return url;
    }

    private Map<String, Class> readRequiredUrlPathVariables(String urlPattern) {
	Map<String, String> keyTypeVariables = readKeyTypeVariablesFromUrlPattern(urlPattern);
	if (keyTypeVariables.isEmpty()) {
	    return new HashMap<>();
	}
	return convertRawToType(keyTypeVariables);
    }

    private Map<String, String> readKeyTypeVariablesFromUrlPattern(String urlPattern) {
	Map<String, String> keyTypeUrlPatternVariables = new HashMap<>();
	String[] urlPatternSegments = urlPattern.split(URL_SEGMENTS_SEPARATOR);
	for (int i = 0; i < urlPatternSegments.length; i++) {
	    if (!isPathVariable(urlPatternSegments[i])) {
		continue;
	    }
	    String pathVariable = readRawPathVariable(urlPatternSegments[i]);
	    String[] keyAndType = pathVariable.split(URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	    if (keyAndType.length >= 2) {
		keyTypeUrlPatternVariables.put(keyAndType[0], keyAndType[1]);
	    }
	}
	return keyTypeUrlPatternVariables;
    }


    @Override
    public Pairs readPathVariables(String url, String urlPattern) {
	List<Pair> pathVariables = new ArrayList<>();
	String[] urlSegments = url.split(URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = urlPattern.split(URL_SEGMENTS_SEPARATOR);
	if (urlSegments.length < urlPatternSegments.length) {
	    return new Pairs(pathVariables);
	}
	try {
	    for (int i = 0; i < urlPatternSegments.length; i++) {
		if (!isPathVariable(urlPatternSegments[i])) {
		    continue;
		}
		pathVariables.add(readPathVariable(urlSegments[i], urlPatternSegments[i]));
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	    pathVariables.clear();
	}
	return new Pairs(pathVariables);
    }

    private Pair readPathVariable(String urlSegment, String urlPatternSegment) {
	String rawPathVariable = readRawPathVariable(urlPatternSegment);
	if (rawPathVariable.isEmpty()) {
	    throw new CreationException();
	}
	String[] keyAndType = readRawPathVariable(urlPatternSegment)
		.split(URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	if (keyAndType.length < 2) {
	    throw new CreationException();
	}
	return new Pair(keyAndType[0], getUrlPatternTypeValue(keyAndType[1], urlSegment));
    }

    private Object getUrlPatternTypeValue(String type, String value) {
	if (UrlPatternType.BOOLEAN.equalsByValue(type)) {
	    return Boolean.parseBoolean(value);
	}
	if (UrlPatternType.INT.equalsByValue(type)) {
	    return Integer.parseInt(value);
	}
	if (UrlPatternType.LONG.equalsByValue(type)) {
	    return Long.parseLong(value);
	}
	if (UrlPatternType.FLOAT.equalsByValue(type)) {
	    return Float.parseFloat(value);
	}
	if (UrlPatternType.DOUBLE.equalsByValue(type)) {
	    return Double.parseDouble(value);
	}
	return value;
    }

    private boolean isPathVariable(String urlPatternSegment) {
	return urlPatternSegment.startsWith(URL_PATTERN_PATH_VARIABLE_START)
		&& urlPatternSegment.endsWith(URL_PATTERN_PATH_VARIABLE_END);
    }

    private String readRawPathVariable(String urlPatternSegment) {
	return urlPatternSegment.substring(1, urlPatternSegment.length() - 1);
    }

    private boolean isNumeric(String string) {
	try {
	    Double.parseDouble(string);
	    return true;
	} catch (Exception exception) {
	    return false;
	}
    }

    private Map<String, Class> readRequiredUrlParameters(String urlPattern) {
	Map<String, String> rawParameters = readRawParameters(urlPattern);
	if (rawParameters.isEmpty()) {
	    return new HashMap<>();
	}
	return convertRawToType(rawParameters);
    }

    private Map<String, Class> convertRawToType(Map<String, String> rawMap) {
	Map<String, Class> typedMap = new HashMap<>();
	try {
	    for (Map.Entry<String, String> rawEntry : rawMap.entrySet()) {
		typedMap.put(rawEntry.getKey(), getVariableType(rawEntry.getValue()).getType());
	    }
	} catch (CreationException exception) {
	    exception.printStackTrace();
	    typedMap.clear();
	}
	return typedMap;
    }

    private UrlPatternType getVariableType(String value) {
	if (UrlPatternType.BOOLEAN.equalsByValue(value)) {
	    return UrlPatternType.BOOLEAN;
	}
	if (UrlPatternType.INT.equalsByValue(value)) {
	    return UrlPatternType.INT;
	}
	if (UrlPatternType.LONG.equalsByValue(value)) {
	    return UrlPatternType.LONG;
	}
	if (UrlPatternType.FLOAT.equalsByValue(value)) {
	    return UrlPatternType.FLOAT;
	}
	if (UrlPatternType.DOUBLE.equalsByValue(value)) {
	    return UrlPatternType.DOUBLE;
	}
	if (UrlPatternType.STRING.equalsByValue(value)) {
	    return UrlPatternType.STRING;
	}
	throw new CreationException(value + " is not recoginizable variable type!");
    }

    private Map<String, String> readRawParameters(String url) {
	Map<String, String> parameters = new HashMap<>();
	int indexOfQuestionMark = url.indexOf(PARAMETERS_BEGINING) + 1;
	String toSplitParameters = url.substring(indexOfQuestionMark, url.length());
	String[] parametersKeyValuePairs = toSplitParameters.split(PARAMETERS_SEPARATOR);
	if (parametersKeyValuePairs.length < 1) {
	    return parameters;
	}
	for (String keyValue : parametersKeyValuePairs) {
	    String[] keyAndValue = keyValue.split(PARAMETERS_KEY_VALUE_SEPARATOR);
	    if (keyAndValue.length >= 2) {
		parameters.put(keyAndValue[0], keyAndValue[1]);
	    }
	}
	return parameters;
    }

    @Override
    public Pairs readParameters(String url, String urlPattern) {
	List<Pair> parameters = new ArrayList<>();
	Map<String, String> rawParameters = readRawParameters(url);
	Map<String, String> requiredUrlParameters = readRawParameters(urlPattern);
	if (rawParameters.size() < requiredUrlParameters.size()) {
	    return new Pairs(parameters);
	}
	try {
	    for(Map.Entry<String, String> requiredUrlParameterEntry : requiredUrlParameters.entrySet()) {
		String toParseValue = rawParameters.getOrDefault(requiredUrlParameterEntry.getKey(), "");
		Object value = getUrlPatternTypeValue(requiredUrlParameterEntry.getValue(), toParseValue);
		parameters.add(new Pair(requiredUrlParameterEntry.getKey(), value));
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	    parameters.clear();
	}
	return new Pairs(parameters);
    }

    @Override
    public boolean hasParameters(String urlPattern) {
	return urlPattern.contains(PARAMETERS_BEGINING) && urlPattern.contains(PARAMETERS_KEY_VALUE_SEPARATOR);
    }

    @Override
    public boolean hasPathVariables(String urlPattern) {
	return urlPattern.contains(URL_PATTERN_PATH_VARIABLE_START)
		&& urlPattern.contains(URL_PATTERN_PATH_VARIABLE_END);
    }

}
