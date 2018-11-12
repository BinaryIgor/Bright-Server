package com.iprogrammerr.bright.server.rule;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public final class RuleThatCanBeObserved<T> extends TypeSafeMatcher<Rule<T>> {

	private final List<T> compliant;
	private final List<T> nonCompliant;

	public RuleThatCanBeObserved(List<T> compliant, List<T> nonCompliant) {
		this.compliant = compliant;
		this.nonCompliant = nonCompliant;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(Rule<T> item) {
		boolean matched = true;
		for (T c : this.compliant) {
			matched = item.isCompliant(c);
			if (!matched) {
				break;
			}
		}
		if (matched) {
			for (T nc : this.nonCompliant) {
				matched = !item.isCompliant(nc);
				if (!matched) {
					break;
				}
			}
		}
		return matched;
	}
}
