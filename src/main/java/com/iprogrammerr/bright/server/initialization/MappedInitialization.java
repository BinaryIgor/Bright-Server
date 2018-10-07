package com.iprogrammerr.bright.server.initialization;

import com.iprogrammerr.bright.server.model.Mapping;

public final class MappedInitialization<From, To> implements Initialization<To> {

    private final From source;
    private final Mapping<From, To> mapping;

    public MappedInitialization(From source, Mapping<From, To> mapping) {
	this.source = source;
	this.mapping = mapping;
    }

    @Override
    public To value() {
	return this.mapping.value(this.source);
    }

}
