package com.iprogrammerr.bright.server.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iprogrammerr.bright.server.initialization.Initialization;
import com.iprogrammerr.bright.server.initialization.StickyInitialization;
import com.iprogrammerr.bright.server.model.KeyValue;
import com.iprogrammerr.bright.server.model.KeysValues;
import com.iprogrammerr.bright.server.model.StringObject;
import com.iprogrammerr.bright.server.model.StringsObjects;

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class TypedUrlPattern implements UrlPattern {

    private static final String SEGMENTS_SEPARATOR = "/";
    private static final String PARAMETERS_BEGINING = "?";
    private static final String PARAMETERS_SEPARATOR = "&";
    private static final String PARAMETERS_KEY_VALUE_SEPARATOR = "=";
    private static final String PATH_VARIABLE_START = "{";
    private static final String PATH_VARIABLE_END = "}";
    private static final String PATH_VARIABLE_KEY_TYPE_SEPARATOR = ":";
    private final String urlPattern;
    private final Type type;
    private final Initialization<Map<String, Class>> pathVariables;
    private final Initialization<Map<String, Class>> parameters;

    public TypedUrlPattern(String urlPattern) {
	this(urlPattern, new UrlPatternType());
    }

    public TypedUrlPattern(String urlPattern, Type type) {
	this.urlPattern = urlPattern;
	this.type = type;
	this.pathVariables = new StickyInitialization<>(() -> pathVariables());
	this.parameters = new StickyInitialization<>(() -> parameters());
    }

    @Override
    public boolean isMatched(String url) {
	boolean matched;
	try {
	    matched = (this.pathVariables.value().isEmpty()
		    || areVariablesValid(this.pathVariables.value(), pathVariables(url)))
		    && (this.parameters.value().isEmpty()
			    || areVariablesValid(this.parameters.value(), parameters(url)));
	} catch (Exception e) {
	    matched = false;
	}
	if (matched) {
	    matched = isBaseMatched(url);
	}
	return matched;
    }

    private boolean areVariablesValid(Map<String, Class> requiredVariables, KeysValues variables) {
	for (Map.Entry<String, Class> rv : requiredVariables.entrySet()) {
	    if (!variables.has(rv.getKey(), rv.getValue())) {
		return false;
	    }
	}
	return true;
    }

    private boolean isBaseMatched(String url) {
	url = withoutParameters(url);
	String[] urlPatternSegments = withoutParameters(this.urlPattern).split(SEGMENTS_SEPARATOR);
	String[] urlSegments = url.split(SEGMENTS_SEPARATOR);
	boolean matched = urlPatternSegments.length == urlSegments.length;
	if (matched) {
	    for (int i = 0; i < urlPatternSegments.length; i++) {
		matched = isPathVariable(urlPatternSegments[i]) || urlPatternSegments[i].equals(urlSegments[i]);
		if (!matched) {
		    break;
		}
	    }
	}
	return matched;
    }

    private String withoutParameters(String url) {
	int parametersBegining = url.indexOf(PARAMETERS_BEGINING);
	if (parametersBegining >= 0) {
	    url = url.substring(0, parametersBegining);
	}
	return url;
    }

    private Map<String, Class> pathVariables() {
	Map<String, String> keyTypeVariables = keyTypeVariables();
	Map<String, Class> pathVariables;
	if (keyTypeVariables.isEmpty()) {
	    pathVariables = new HashMap<>();
	} else {
	    pathVariables = typed(keyTypeVariables);
	}
	return pathVariables;
    }

    private Map<String, String> keyTypeVariables() {
	Map<String, String> keyTypeVariables = new HashMap<>();
	String[] urlPatternSegments = this.urlPattern.split(SEGMENTS_SEPARATOR);
	for (int i = 0; i < urlPatternSegments.length; i++) {
	    if (!isPathVariable(urlPatternSegments[i])) {
		continue;
	    }
	    String pathVariable = rawPathVariable(urlPatternSegments[i]);
	    String[] keyType = pathVariable.split(PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	    if (keyType.length >= 2) {
		keyTypeVariables.put(keyType[0], keyType[1]);
	    }
	}
	return keyTypeVariables;
    }

    @Override
    public KeysValues pathVariables(String url) {
	List<KeyValue> pathVariables = new ArrayList<>();
	String[] urlSegments = url.split(SEGMENTS_SEPARATOR);
	String[] urlPatternSegments = this.urlPattern.split(SEGMENTS_SEPARATOR);
	if (urlSegments.length >= urlPatternSegments.length) {
	    try {
		for (int i = 0; i < urlPatternSegments.length; i++) {
		    if (isPathVariable(urlPatternSegments[i])) {
			pathVariables.add(pathVariable(urlSegments[i], urlPatternSegments[i]));
		    }
		}
	    } catch (Exception e) {
		e.printStackTrace();
		pathVariables.clear();
	    }
	}
	return new StringsObjects(pathVariables);
    }

    private KeyValue pathVariable(String urlSegment, String urlPatternSegment) throws Exception {
	String rawPathVariable = rawPathVariable(urlPatternSegment);
	if (rawPathVariable.isEmpty()) {
	    throw new Exception("Path variable is empty");
	}
	String[] keyType = rawPathVariable.split(PATH_VARIABLE_KEY_TYPE_SEPARATOR);
	if (keyType.length < 2) {
	    throw new Exception("Path variable is empty");
	}
	return new StringObject(keyType[0], this.type.value(this.pathVariables.value().get(keyType[0]), urlSegment));
    }

    private boolean isPathVariable(String urlPatternSegment) {
	return urlPatternSegment.startsWith(PATH_VARIABLE_START) && urlPatternSegment.endsWith(PATH_VARIABLE_END);
    }

    private String rawPathVariable(String urlPatternSegment) {
	return urlPatternSegment.substring(1, urlPatternSegment.length() - 1);
    }

    private Map<String, Class> parameters() {
	Map<String, String> rawParameters = rawParameters(this.urlPattern);
	Map<String, Class> parameters;
	if (rawParameters.isEmpty()) {
	    parameters = new HashMap<>();
	} else {
	    parameters = typed(rawParameters);
	}
	return parameters;
    }

    private Map<String, Class> typed(Map<String, String> raw) {
	Map<String, Class> typed = new HashMap<>();
	try {
	    for (Map.Entry<String, String> entry : raw.entrySet()) {
		typed.put(entry.getKey(), this.type.type(entry.getValue()));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    typed.clear();
	}
	return typed;
    }

    private Map<String, String> rawParameters(String url) {
	Map<String, String> parameters = new HashMap<>();
	int parametersBegining = url.indexOf(PARAMETERS_BEGINING);
	String toSplit = url.substring(parametersBegining + 1, url.length());
	String[] parametersKeysValues = toSplit.split(PARAMETERS_SEPARATOR);
	if (parametersKeysValues.length > 0) {
	    for (String pkv : parametersKeysValues) {
		String[] kv = pkv.split(PARAMETERS_KEY_VALUE_SEPARATOR);
		if (kv.length >= 2) {
		    parameters.put(kv[0], kv[1]);
		}
	    }
	}
	return parameters;
    }

    @Override
    public KeysValues parameters(String url) {
	List<KeyValue> typedParameters = new ArrayList<>();
	Map<String, String> urlParameters = rawParameters(url);
	if (urlParameters.size() >= this.parameters.value().size()) {
	    try {
		for (Map.Entry<String, Class> pe : this.parameters.value().entrySet()) {
		    String toParseValue = urlParameters.getOrDefault(pe.getKey(), "");
		    Object value = this.type.value(pe.getValue(), toParseValue);
		    typedParameters.add(new StringObject(pe.getKey(), value));
		}
	    } catch (Exception e) {
		e.printStackTrace();
		typedParameters.clear();
	    }
	}
	return new StringsObjects(typedParameters);
    }

    @Override
    public boolean hasParameters() {
	return this.urlPattern.contains(PARAMETERS_BEGINING)
		&& this.urlPattern.contains(PARAMETERS_KEY_VALUE_SEPARATOR);
    }

    @Override
    public boolean hasPathVariables() {
	return this.urlPattern.contains(PATH_VARIABLE_START) && this.urlPattern.contains(PATH_VARIABLE_END);
    }

}
