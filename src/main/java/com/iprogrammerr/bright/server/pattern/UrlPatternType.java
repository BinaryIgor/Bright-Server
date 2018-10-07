package com.iprogrammerr.bright.server.pattern;

public class UrlPatternType implements Type {

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

}
