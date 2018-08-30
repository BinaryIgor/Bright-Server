package com.iprogrammerr.simple.http.server.rule;

public interface RequestMethodRule {

    boolean isCompliant(String requestMethod);
}
