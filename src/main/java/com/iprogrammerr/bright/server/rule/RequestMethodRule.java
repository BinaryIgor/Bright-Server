package com.iprogrammerr.bright.server.rule;

public interface RequestMethodRule {
    boolean isCompliant(String requestMethod);
}
