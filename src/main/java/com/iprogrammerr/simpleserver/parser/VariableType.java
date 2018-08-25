package com.iprogrammerr.simpleserver.parser;

import com.iprogrammerr.simpleserver.exception.CreationException;

public enum VariableType {

    BOOLEAN("boolean", Boolean.class), INT("int", Integer.class), DOUBLE("double", Double.class), STRING("string",
	    String.class);

    private String value;
    private Class type;

    private VariableType(String value, Class type) {
	this.value = value;
	this.type = type;
    }

    public String getValue() {
	return value;
    }

    public Class getType() {
	return type;
    }

    public static VariableType createFromString(String type) {
	type = type.toLowerCase();
	if (BOOLEAN.value.equals(type)) {
	    return BOOLEAN;
	}
	if (INT.value.equals(type)) {
	    return INT;
	}
	if (DOUBLE.value.equals(type)) {
	    return DOUBLE;
	}
	if (STRING.value.equals(type)) {
	    return STRING;
	}
	throw new CreationException(type + "is not a valid type");

    }

}
