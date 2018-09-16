package com.iprogrammerr.bright.server.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iprogrammerr.bright.server.model.KeyValue;
import com.iprogrammerr.bright.server.model.KeysValues;
import com.iprogrammerr.bright.server.model.StringObject;
import com.iprogrammerr.bright.server.model.StringsObjects;

public class TypedUrlPattern implements UrlPattern {

    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private static final String PARAMETERS_BEGINING = "?";
    private static final String PARAMETERS_SEPARATOR = "&";
    private static final String PARAMETERS_KEY_VALUE_SEPARATOR = "=";
    private static final String URL_PATTERN_PATH_VARIABLE_START = "{";
    private static final String URL_PATTERN_PATH_VARIABLE_END = "}";
    private static final String URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR = ":";
    private final String urlPattern;
    private Type type;

    public TypedUrlPattern(String urlPattern) {
	this(urlPattern, new UrlPatternType());
    }

    public TypedUrlPattern(String urlPattern, Type type) {
	this.urlPattern = urlPattern;
	this.type = type;
    }

    @Override
    public boolean match(String url) {
	try {
	    Map<String, Class> requiredUrlPathVariables = readRequiredUrlPathVariables();
	    boolean haveRequiredUrlPathVariables = requiredUrlPathVariables.isEmpty()
		    || checkVariables(requiredUrlPathVariables, readPathVariables(url));
	    if (!haveRequiredUrlPathVariables) {
		return false;
	    }
	    Map<String, Class> requiredUrlParameters = readRequiredUrlParameters();
	    boolean haveRequiredUrlParameters = requiredUrlParameters.isEmpty()
		    || checkVariables(requiredUrlParameters, readParameters(url));
	    if (!haveRequiredUrlParameters) {
		return false;
	    }
	} catch (Exception exception) {
	    return false;
	}
	return matchOmittingPathVariablesAndParameters(url);
    }

    private boolean checkVariables(Map<String, Class> requiredVariables, KeysValues variables) {
	for (Map.Entry<String, Class> requiredVariable : requiredVariables.entrySet()) {
	    if (!variables.has(requiredVariable.getKey(), requiredVariable.getValue())) {
		return false;
	    }
	}
	return true;
    }

    private boolean matchOmittingPathVariablesAndParameters(String url) {
	String withoutParametersUrlPattern = cutParameters(urlPattern);
	url = cutParameters(url);
	String[] urlPatternSegments = withoutParametersUrlPattern.split(URL_SEGMENTS_SEPARATOR);
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

    private Map<String, Class> readRequiredUrlPathVariables() throws Exception {
	Map<String, String> keyTypeVariables = readKeyTypeVariablesFromUrlPattern();
	if (keyTypeVariables.isEmpty()) {
	    return new HashMap<>();
	}
	return convertRawToType(keyTypeVariables);
    }

    private Map<String, String> readKeyTypeVariablesFromUrlPattern() {
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
    public KeysValues readPathVariables(String url) {
	List<KeyValue> pathVariables = new ArrayList<>();
	String[] urlSegments = url.split(URL_SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = urlPattern.split(URL_SEGMENTS_SEPARATOR);
	if (urlSegments.length < urlPatternSegments.length) {
	    return new StringsObjects(pathVariables);
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
	return new StringsObjects(pathVariables);
    }

    private KeyValue readPathVariable(String urlSegment, String urlPatternSegment) throws Exception {
	String rawPathVariable = readRawPathVariable(urlPatternSegment);
	if (rawPathVariable.isEmpty()) {
	    throw new Exception("Path variable is empty");
	}
	String[] keyAndType = readRawPathVariable(urlPatternSegment)
		.split(URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	if (keyAndType.length < 2) {
	    throw new Exception("Path variable is empty");
	}
	return new StringObject(keyAndType[0], type.value(keyAndType[1], urlSegment));
    }

    private boolean isPathVariable(String urlPatternSegment) {
	return urlPatternSegment.startsWith(URL_PATTERN_PATH_VARIABLE_START)
		&& urlPatternSegment.endsWith(URL_PATTERN_PATH_VARIABLE_END);
    }

    private String readRawPathVariable(String urlPatternSegment) {
	return urlPatternSegment.substring(1, urlPatternSegment.length() - 1);
    }

    private Map<String, Class> readRequiredUrlParameters() throws Exception {
	Map<String, String> rawParameters = readRawParameters(urlPattern);
	if (rawParameters.isEmpty()) {
	    return new HashMap<>();
	}
	return convertRawToType(rawParameters);
    }

    private Map<String, Class> convertRawToType(Map<String, String> rawMap) throws Exception {
	Map<String, Class> typedMap = new HashMap<>();
	try {
	    for (Map.Entry<String, String> rawEntry : rawMap.entrySet()) {
		typedMap.put(rawEntry.getKey(), type.type(rawEntry.getValue()));
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	    typedMap.clear();
	}
	return typedMap;
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
    public KeysValues readParameters(String url) {
	List<KeyValue> parameters = new ArrayList<>();
	Map<String, String> rawParameters = readRawParameters(url);
	Map<String, String> requiredUrlParameters = readRawParameters(urlPattern);
	if (rawParameters.size() < requiredUrlParameters.size()) {
	    return new StringsObjects(parameters);
	}
	try {
	    for (Map.Entry<String, String> requiredUrlParameterEntry : requiredUrlParameters.entrySet()) {
		String toParseValue = rawParameters.getOrDefault(requiredUrlParameterEntry.getKey(), "");
		Object value = type.value(requiredUrlParameterEntry.getValue(), toParseValue);
		parameters.add(new StringObject(requiredUrlParameterEntry.getKey(), value));
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	    parameters.clear();
	}
	return new StringsObjects(parameters);
    }

    @Override
    public boolean hasParameters() {
	return urlPattern.contains(PARAMETERS_BEGINING) && urlPattern.contains(PARAMETERS_KEY_VALUE_SEPARATOR);
    }

    @Override
    public boolean hasPathVariables() {
	return urlPattern.contains(URL_PATTERN_PATH_VARIABLE_START)
		&& urlPattern.contains(URL_PATTERN_PATH_VARIABLE_END);
    }

}
