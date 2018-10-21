package com.iprogrammerr.bright.server.pattern;

public final class UrlPatternType implements Type {

    private static final String BOOLEAN = "boolean";
    private static final String INT = "int";
    private static final String LONG = "long";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";

    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<T> type(String type) {
	Class<T> typeClass;
	if (type.equalsIgnoreCase(BOOLEAN)) {
	    typeClass = (Class<T>) Boolean.class;
	} else if (type.equalsIgnoreCase(INT)) {
	    typeClass = (Class<T>) Integer.class;
	} else if (type.equalsIgnoreCase(LONG)) {
	    typeClass = (Class<T>) Long.class;
	} else if (type.equalsIgnoreCase(FLOAT)) {
	    typeClass = (Class<T>) Float.class;
	} else if (type.equalsIgnoreCase(DOUBLE)) {
	    typeClass = (Class<T>) Double.class;
	} else {
	    typeClass = (Class<T>) String.class;
	}
	return typeClass;
    }

    @Override
    public <T> Object value(Class<T> type, String value) throws Exception {
	value = value.trim();
	Object parsedValue;
	if (Boolean.class.isAssignableFrom(type)) {
	    parsedValue = Boolean.parseBoolean(value);
	} else if (Integer.class.isAssignableFrom(type)) {
	    parsedValue = Integer.parseInt(value);
	} else if (Long.class.isAssignableFrom(type)) {
	    parsedValue = Long.parseLong(value);
	} else if (Float.class.isAssignableFrom(type)) {
	    parsedValue = Float.parseFloat(value);
	} else if (Double.class.isAssignableFrom(type)) {
	    parsedValue = Double.parseDouble(value);
	} else {
	    parsedValue = value;
	}
	return parsedValue;
    }

    @Override
    public Object probedValue(String value) {
	Object probed;
	try {
	    if (isInteger(value)) {
		long longValue = Long.parseLong(value);
		if (longValue > Integer.MAX_VALUE) {
		    probed = longValue;
		} else {
		    probed = (int) longValue;
		}
	    } else if (isDouble(value)) {
		probed = Double.parseDouble(value);
	    } else if (isBoolean(value)) {
		probed = Boolean.parseBoolean(value);
	    } else {
		probed = value;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    probed = value;
	}
	return probed;
    }

    private boolean isInteger(String value) {
	char[] values = value.toCharArray();
	boolean is = false;
	if (values.length == 0) {
	    is = false;
	} else if (values.length == 1 && Character.isDigit(values[0])) {
	    is = true;
	} else if (Character.isDigit(values[0]) || values[0] == '-' || values[0] == '+') {
	    is = notDigitIndex(1, values) >= 0 ? false : true;
	}
	return is;
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

    private boolean isDouble(String value) {
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
