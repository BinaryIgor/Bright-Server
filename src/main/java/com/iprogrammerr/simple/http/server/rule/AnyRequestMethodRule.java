package com.iprogrammerr.simple.http.server.rule;

public class AnyRequestMethodRule implements RequestMethodRule {

    @Override
    public boolean isCompliant(String requestMethod) {
	return true;
    }

}
