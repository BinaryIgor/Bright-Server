package com.iprogrammerr.bright.server.rule;

public interface RequestMethodRule {
    boolean compliant(String requestMethod);
}
