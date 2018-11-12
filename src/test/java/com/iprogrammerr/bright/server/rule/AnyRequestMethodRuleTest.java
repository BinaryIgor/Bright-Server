package com.iprogrammerr.bright.server.rule;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.rule.method.AnyRequestMethodRule;

public final class AnyRequestMethodRuleTest {

	@Test
	public void canBeObserved() {
		assertThat(new AnyRequestMethodRule(), new RuleThatCanBeObserved<>(
				Arrays.asList("get", "post", "put", "delete", "trace", "patch", "head", "options"), new ArrayList<>()));
	}
}
