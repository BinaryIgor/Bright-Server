package com.iprogrammerr.bright.server.pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.model.Primitives;

public final class ParameterizableUrlPatternThatCanReadPathVariablesAndParameters extends TypeSafeMatcher<ParameterizableUrlPattern> {

	private final String url;
	private final Primitives parameters;
	private final Primitives pathVariables;

	public ParameterizableUrlPatternThatCanReadPathVariablesAndParameters(String url, Primitives parameters,
			Primitives pathVariables) {
		this.url = url;
		this.parameters = parameters;
		this.pathVariables = pathVariables;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(String.format("%s that read from %s %s parameters and %s path variables",
				getClass().getSimpleName(), this.url, this.parameters, this.pathVariables));
	}

	@Override
	protected void describeMismatchSafely(ParameterizableUrlPattern item, Description description) {
		description
				.appendText(String.format("%s parameters and  %s path variables", this.parameters, this.pathVariables));
	}

	@Override
	protected boolean matchesSafely(ParameterizableUrlPattern item) {
		return this.parameters.equals(item.parameters(this.url))
				&& this.pathVariables.equals(item.pathVariables(this.url));
	}
}
