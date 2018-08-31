package com.iprogrammerr.bright.server.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iprogrammerr.bright.server.exception.CreationException;
import com.iprogrammerr.bright.server.model.Pair;
import com.iprogrammerr.bright.server.model.Pairs;

public class ResolverUrlPatternParser {

    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private static final String PARAMETERS_BEGINING = "?";
    private static final String PARAMETERS_SEPARATOR = "&";
    private static final String PARAMETERS_KEY_VALUE_SEPARATOR = "=";
    private static final String URL_PATTERN_PATH_VARIABLE_START = "{";
    private static final String URL_PATTERN_PATH_VARIABLE_END = "}";
    private static final String URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR = ":";
    private static final double IS_INTEGER_DELTA = 10e6;

    public boolean match(String url, String urlPattern) {
	System.out.println(url + " vs " + urlPattern);
	Map<String, Class> requiredUrlPathVariables = getRequiredUrlPathVariables(urlPattern);
	if (!requiredUrlPathVariables.isEmpty() && checkVariables(url, requiredUrlPathVariables, getParameters(url))) {
	    return false;
	}
	Map<String, Class> requiredUrlParameters = getRequiredUrlParameters(urlPattern);
	if (!requiredUrlParameters.isEmpty()
		&& checkVariables(url, requiredUrlParameters, getPathVariables(url, urlPattern))) {
	    return false;
	}
	return matchOmittingPathVariablesAndParameters(url, urlPattern);
    }

    private boolean checkVariables(String url, Map<String, Class> requiredVariables, Pairs variables) {
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

    public Map<String, Class> getRequiredUrlPathVariables(String urlPattern) {
	Map<String, String> keyTypeVariables = getKeyTypeVariablesFromUrlPattern(urlPattern);
	if (keyTypeVariables.isEmpty()) {
	    return new HashMap<>();
	}
	return convertRawToType(keyTypeVariables);
    }

    private Map<String, String> getKeyTypeVariablesFromUrlPattern(String urlPattern) {
	Map<String, String> keyTypeUrlPatternVariables = new HashMap<>();
	String[] urlPatternSegments = urlPattern.split(URL_SEGMENTS_SEPARATOR);
	for (int i = 0; i < urlPatternSegments.length; i++) {
	    if (!isPathVariable(urlPatternSegments[i])) {
		continue;
	    }
	    String pathVariable = getRawPathVariable(urlPatternSegments[i]);
	    String[] keyAndType = pathVariable.split(URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	    if (keyAndType.length >= 2) {
		keyTypeUrlPatternVariables.put(keyAndType[0], keyAndType[1]);
	    }
	}
	return keyTypeUrlPatternVariables;
    }

    public Map<String, String> getRawPathVariables(String url, String urlPattern) {
	Map<String, String> pathVariables = new HashMap<>();
	String[] urlSegments = url.split(URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = urlPattern.split(URL_SEGMENTS_SEPARATOR);
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

    public Pairs getPathVariables(String url, String urlPattern) {
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
		pathVariables.add(getPathVariable(urlSegments[i], urlPatternSegments[i]));
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	    pathVariables.clear();
	}
	return new Pairs(pathVariables);
    }

    private Pair getPathVariable(String urlSegment, String urlPatternSegment) {
	String rawPathVariable = getRawPathVariable(urlPatternSegment);
	if (rawPathVariable.isEmpty()) {
	    throw new CreationException();
	}
	String[] keyAndType = getRawPathVariable(urlPatternSegment).split(URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	if (keyAndType.length < 2) {
	    throw new CreationException();
	}
	if (UrlPatternType.BOOLEAN.equalsByValue(keyAndType[1])) {
	    return new Pair(keyAndType[0], Boolean.parseBoolean(urlSegment));
	}
	if (UrlPatternType.INT.equalsByValue(keyAndType[1])) {
	    return new Pair(keyAndType[0], Integer.parseInt(urlSegment));
	}
	if (UrlPatternType.LONG.equalsByValue(keyAndType[1])) {
	    return new Pair(keyAndType[0], Long.parseLong(urlSegment));
	}
	if (UrlPatternType.FLOAT.equalsByValue(keyAndType[1])) {
	    return new Pair(keyAndType[0], Float.parseFloat(urlSegment));
	}
	if (UrlPatternType.DOUBLE.equalsByValue(keyAndType[1])) {
	    return new Pair(keyAndType[0], Double.parseDouble(urlSegment));
	}
	return new Pair(keyAndType[0], urlSegment);
    }

    private boolean isPathVariable(String urlPatternSegment) {
	return urlPatternSegment.startsWith(URL_PATTERN_PATH_VARIABLE_START)
		&& urlPatternSegment.endsWith(URL_PATTERN_PATH_VARIABLE_END);
    }

    private String getRawPathVariable(String urlPatternSegment) {
	return urlPatternSegment.substring(1, urlPatternSegment.length() - 1);
    }

    public boolean areEqual(String url, String urlPattern) {
	urlPattern = getWithoutParametersUrl(urlPattern);
	String[] urlSegments = url.split(URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = urlPattern.split(URL_SEGMENTS_SEPARATOR);
	if (urlSegments.length != urlPatternSegments.length) {
	    return false;
	}
	for (int i = 0; i < urlSegments.length; i++) {
	    if (isPathVariable(urlPatternSegments[i])) {
		continue;
	    }
	    if (!urlSegments[i].equals(urlPatternSegments[i])) {
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
	return convertRawToType(rawParameters);
    }

    private Map<String, Class> convertRawToType(Map<String, String> rawMap) {
	Map<String, Class> typedMap = new HashMap<>();
	try {
	    for (Map.Entry<String, String> rawEntry : rawMap.entrySet()) {
		typedMap.put(rawEntry.getKey(), getVariableType(rawEntry.getValue()).getClass());
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
	throw new CreationException(value + " is not recoginizable variable type!");
    }

    private Object getParameterValue(String parameter) {
	if (!isNumeric(parameter)) {
	    return parameter;
	}
	double numericParameter = Double.parseDouble(parameter);
	double delta = numericParameter - Math.floor(numericParameter);
	if (delta < IS_INTEGER_DELTA) {
	    return numericParameter;
	}
	return (int) numericParameter;
    }

    private Map<String, String> getRawParameters(String path) {
	Map<String, String> parameters = new HashMap<>();
	int indexOfQuestionMark = path.indexOf(PARAMETERS_BEGINING) + 1;
	String toSplitParameters = path.substring(indexOfQuestionMark, path.length());
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

    public Pairs getParameters(String path) {
	List<Pair> parameters = new ArrayList<>();
	Map<String, String> rawParameters = getRawParameters(path);
	if (rawParameters.isEmpty()) {
	    return new Pairs(parameters);
	}
	for (Map.Entry<String, String> rawParameter : rawParameters.entrySet()) {
	    parameters.add(new Pair(rawParameter.getKey(), getParameterValue(rawParameter.getValue())));
	}
	return new Pairs(parameters);
    }

    public String getWithoutParametersUrl(String url) {
	int indexOfParametersBegining = url.indexOf(PARAMETERS_BEGINING);
	if (indexOfParametersBegining < 0) {
	    return url;
	}
	return url.substring(0, indexOfParametersBegining);
    }

    public boolean hasParameters(String urlPattern) {
	return urlPattern.contains(PARAMETERS_BEGINING) && urlPattern.contains(PARAMETERS_KEY_VALUE_SEPARATOR);
    }

    public boolean hasPathVariables(String urlPattern) {
	return urlPattern.contains(URL_PATTERN_PATH_VARIABLE_START)
		&& urlPattern.contains(URL_PATTERN_PATH_VARIABLE_END);
    }

}
