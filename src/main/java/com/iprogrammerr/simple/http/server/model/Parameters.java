package com.iprogrammerr.simple.http.server.model;

import java.util.List;

public class Parameters extends Pairs<Parameter> {

    public Parameters(List<Parameter> parameters) {
	super(parameters);
    }

    @Override
    public String toString() {
	return "Parameters [pairs=" + pairs + "]";
    }

}
