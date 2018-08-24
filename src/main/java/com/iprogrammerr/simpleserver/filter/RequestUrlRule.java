package com.iprogrammerr.simpleserver.filter;

public class RequestUrlRule {

    private String url;
    private ValidationRule validationRule;

    private RequestUrlRule(String url, ValidationRule validationRule) {
	this.url = url;
	this.validationRule = validationRule;
    }

    public static RequestUrlRule createExact(String url) {
	return new RequestUrlRule(url, ValidationRule.EXACT);
    }

    public static RequestUrlRule createStartsWith(String url) {
	return new RequestUrlRule(url, ValidationRule.STARTS_WITH);
    }

    public boolean isCompliant(String url) {
	if (ValidationRule.EXACT.equals(validationRule)) {
	    return this.url.equals(url);
	}
	System.out.println("Comparing..." + url + "vs " + this.url);
	return url.startsWith(this.url);
    }

    private enum ValidationRule {
	EXACT, STARTS_WITH
    }
}
