package com.iprogrammerr.bright.server.pattern;

public final class UrlPatternType implements Type {

	private static final String BOOLEAN = "boolean";
	private static final String NUMBER = "number";

	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<T> type(String type) {
		Class<T> typeClass;
		if (type.equalsIgnoreCase(BOOLEAN)) {
			typeClass = (Class<T>) Boolean.class;
		} else if (type.equalsIgnoreCase(NUMBER)) {
			typeClass = (Class<T>) Number.class;
		} else {
			typeClass = (Class<T>) String.class;
		}
		return typeClass;
	}

	@Override
	public <T> T value(Class<T> type, String value) throws Exception {
		value = value.trim();
		T parsedValue;
		if (Boolean.class.isAssignableFrom(type)) {
			parsedValue = type.cast(Boolean.parseBoolean(value));
		} else if (Number.class.isAssignableFrom(type)) {
			parsedValue = type.cast(Double.parseDouble(value));
		} else {
			parsedValue = type.cast(value);
		}
		return parsedValue;
	}

	@Override
	public Object probedValue(String value) {
		Object probed;
		try {
			if (isNumber(value)) {
				probed = Double.parseDouble(value);
			} else if (isBoolean(value)) {
				probed = Boolean.parseBoolean(value);
			} else {
				probed = value;
			}
		} catch (Exception e) {
			probed = value;
		}
		return probed;
	}

	private int notDigitIndex(int from, char[] values) {
		int index = -1;
		for (int i = from; i < values.length; ++i) {
			if (!Character.isDigit(values[i])) {
				index = i;
				break;
			}
		}
		return index;
	}

	private boolean isNumber(String value) {
		char[] values = value.toCharArray();
		boolean is = values.length > 0 && Character.isDigit(values[0])
				|| ((values[0] == '-' || values[0] == '+') && values.length > 1);
		if (is) {
			int index = notDigitIndex(1, values);
			if (index > 0 && values[index] == '.') {
				is = notDigitIndex(index + 1, values) >= 0 ? false : true;
			} else {
				is = true;
			}
		}
		return is;
	}

	private boolean isBoolean(String value) {
		return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
	}
}
