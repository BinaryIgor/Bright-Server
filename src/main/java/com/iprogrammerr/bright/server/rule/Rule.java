package com.iprogrammerr.bright.server.rule;

public interface Rule<T> {
    boolean isCompliant(T value);
}
