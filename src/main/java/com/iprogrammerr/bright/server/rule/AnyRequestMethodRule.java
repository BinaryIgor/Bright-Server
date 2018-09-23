package com.iprogrammerr.bright.server.rule;

public final class AnyRequestMethodRule implements RequestMethodRule {

    @Override
    public boolean compliant(String requestMethod) {
	return true;
    }

}
