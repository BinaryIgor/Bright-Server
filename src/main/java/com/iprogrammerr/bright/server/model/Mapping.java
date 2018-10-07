package com.iprogrammerr.bright.server.model;

public interface Mapping<From, To> {
    To value(From from);
}
