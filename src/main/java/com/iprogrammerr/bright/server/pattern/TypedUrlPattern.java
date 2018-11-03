package com.iprogrammerr.bright.server.pattern;

import java.util.HashMap;
import java.util.Map;

import com.iprogrammerr.bright.server.initialization.Initialization;
import com.iprogrammerr.bright.server.initialization.StickyInitialization;
import com.iprogrammerr.bright.server.model.Attributes;
import com.iprogrammerr.bright.server.model.TypedMap;

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

	private TypedUrlPattern(String urlPattern, Type type,
			Initialization<Map<String, Class>> pathVariables,
			Initialization<Map<String, Class>> parameters) {
		this.urlPattern = urlPattern;
		this.type = type;
		this.pathVariables = pathVariables;
		this.parameters = parameters;
	}

	private TypedUrlPattern(String urlPattern, Type type) {
		this(urlPattern, type, new StickyInitialization<>(() -> {
			Map<String, Class> pathVariables = new HashMap<>();
			String[] urlPatternSegments = urlPattern.split(SEGMENTS_SEPARATOR);
			for (int i = 0; i < urlPatternSegments.length; i++) {
				boolean pathVariable = urlPatternSegments[i].startsWith(PATH_VARIABLE_START)
						&& urlPatternSegments[i].endsWith(PATH_VARIABLE_END);
				if (!pathVariable) {
					continue;
				}
				String raw = urlPatternSegments[i].substring(1, urlPatternSegments[i].length() - 1);
				String[] keyType = raw.split(PATH_VARIABLE_KEY_TYPE_SEPARATOR);
				if (keyType.length >= 2) {
					pathVariables.put(keyType[0], type.type(keyType[1]));
				}
			}
			return pathVariables;
		}), new StickyInitialization<>(() -> {
			Map<String, Class> parameters = new HashMap<>();
			int parametersBegining = urlPattern.indexOf(PARAMETERS_BEGINING);
			String toSplit = urlPattern.substring(parametersBegining + 1, urlPattern.length());
			String[] parametersKeysValues = toSplit.split(PARAMETERS_SEPARATOR);
			if (parametersKeysValues.length > 0) {
				for (String pkv : parametersKeysValues) {
					String[] kv = pkv.split(PARAMETERS_KEY_VALUE_SEPARATOR);
					if (kv.length >= 2) {
						parameters.put(kv[0], type.type(kv[1]));
					}
				}
			}
			return parameters;
		}));
	}

	public TypedUrlPattern(String urlPattern) {
		this(urlPattern, new UrlPatternType());
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

	private boolean areVariablesValid(Map<String, Class> requiredVariables, TypedMap variables) {
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
				matched = isPathVariable(urlPatternSegments[i])
						|| urlPatternSegments[i].equals(urlSegments[i]);
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

	@Override
	public TypedMap pathVariables(String url) {
		TypedMap pathVariables = new Attributes();
		String[] urlSegments = url.split(SEGMENTS_SEPARATOR);
		String[] urlPatternSegments = this.urlPattern.split(SEGMENTS_SEPARATOR);
		if (urlSegments.length >= urlPatternSegments.length) {
			try {
				for (int i = 0; i < urlPatternSegments.length; i++) {
					if (isPathVariable(urlPatternSegments[i])) {
						String rawPathVariable = rawPathVariable(urlPatternSegments[i]);
						if (rawPathVariable.isEmpty()) {
							break;
						}
						String[] keyType = rawPathVariable.split(PATH_VARIABLE_KEY_TYPE_SEPARATOR);
						if (keyType.length < 2) {
							break;
						}
						pathVariables.put(keyType[0], this.type
								.value(this.pathVariables.value().get(keyType[0]), urlSegments[i]));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pathVariables;
	}

	private boolean isPathVariable(String urlPatternSegment) {
		return urlPatternSegment.startsWith(PATH_VARIABLE_START)
				&& urlPatternSegment.endsWith(PATH_VARIABLE_END);
	}

	private String rawPathVariable(String urlPatternSegment) {
		return urlPatternSegment.substring(1, urlPatternSegment.length() - 1);
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
	public TypedMap parameters(String url) {
		TypedMap parameters = new Attributes();
		Map<String, String> urlParameters = rawParameters(url);
		if (urlParameters.size() >= this.parameters.value().size()) {
			try {
				for (Map.Entry<String, Class> entry : this.parameters.value().entrySet()) {
					String toParseValue = urlParameters.getOrDefault(entry.getKey(), "");
					Object value = this.type.value(entry.getValue(), toParseValue);
					parameters.put(entry.getKey(), value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (urlParameters.size() > this.parameters.value().size()) {
			for (Map.Entry<String, String> entry : urlParameters.entrySet()) {
				if (!this.parameters.value().containsKey(entry.getKey())) {
					String toParseValue = urlParameters.getOrDefault(entry.getKey(), "");
					parameters.put(entry.getKey(), this.type.probedValue(toParseValue));
				}
			}
		}
		return parameters;
	}

	@Override
	public boolean hasParameters() {
		return this.urlPattern.contains(PARAMETERS_BEGINING)
				&& this.urlPattern.contains(PARAMETERS_KEY_VALUE_SEPARATOR);
	}

	@Override
	public boolean hasPathVariables() {
		return this.urlPattern.contains(PATH_VARIABLE_START)
				&& this.urlPattern.contains(PATH_VARIABLE_END);
	}

}
