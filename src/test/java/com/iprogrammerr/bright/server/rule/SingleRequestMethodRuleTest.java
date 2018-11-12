package com.iprogrammerr.bright.server.rule;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.rule.method.SingleRequestMethodRule;

public final class SingleRequestMethodRuleTest {

	@Test
	public void canBeObserved() {
		assertThat(new SingleRequestMethodRule(new GetMethod()),
				new RuleThatCanBeObserved<>(Arrays.asList("get"), Arrays.asList("get", "head", "patch")));
	}
}
