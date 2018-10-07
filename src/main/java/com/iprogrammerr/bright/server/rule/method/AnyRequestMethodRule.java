package com.iprogrammerr.bright.server.rule.method;

public final class AnyRequestMethodRule implements RequestMethodRule {

    @Override
    public boolean isCompliant(String requestMethod) {
	return true;
    }

}
