package com.iprogrammerr.bright.server.pattern;

public class UrlPatternType implements Type {

    private static final String BOOLEAN = "boolean";
    private static final String INT = "int";
    private static final String LONG = "long";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";

    @Override
    public <T> Class<T> type(String type) {
	if (type.equalsIgnoreCase(BOOLEAN)) {
	    return (Class<T>) Boolean.class;
	}
	if (type.equalsIgnoreCase(INT)) {
	    return (Class<T>) Integer.class;
	}
	if (type.equalsIgnoreCase(LONG)) {
	    return (Class<T>) Long.class;
	}
	if (type.equalsIgnoreCase(FLOAT)) {
	    return (Class<T>) Float.class;
	}
	if (type.equalsIgnoreCase(DOUBLE)) {
	    return (Class<T>) Double.class;
	}
	return (Class<T>) String.class;
    }

    @Override
    public Object value(String type, String value) throws Exception {
	Class clazz = type(type);
	Object parsedValue;
	if (Boolean.class.isAssignableFrom(clazz)) {
	    parsedValue = Boolean.parseBoolean(value);
	} else if (Integer.class.isAssignableFrom(clazz)) {
	    parsedValue = Integer.parseInt(value);
	} else if (Long.class.isAssignableFrom(clazz)) {
	    parsedValue = Long.parseLong(value);
	} else if (Float.class.isAssignableFrom(clazz)) {
	    parsedValue = Float.parseFloat(value);
	} else if (Double.class.isAssignableFrom(clazz)) {
	    parsedValue = Double.parseDouble(value);
	} else {
	    parsedValue = value;
	}
	return parsedValue;
    }

}
