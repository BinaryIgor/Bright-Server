package com.iprogrammerr.simple.http.server.parser;

public enum UrlPatternType {

    BOOLEAN("boolean", Boolean.class), INT("int", Integer.class), LONG("long", Long.class), FLOAT("float",
	    Float.class), DOUBLE("double", Double.class), STRING("string", String.class);

    private String value;
    private Class type;

    private UrlPatternType(String value, Class type) {
	this.value = value;
	this.type = type;
    }

    public String getValue() {
	return value;
    }

    public Class getType() {
	return type;
    }

    public boolean equalsByValue(String value) {
	return this.value.equalsIgnoreCase(value);

    }

}
