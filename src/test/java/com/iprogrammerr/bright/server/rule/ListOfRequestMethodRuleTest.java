package com.iprogrammerr.bright.server.rule;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.PutMethod;
import com.iprogrammerr.bright.server.rule.method.ListOfRequestMethodRule;

public final class ListOfRequestMethodRuleTest {

	@Test
	public void canBeObserved() {
		assertThat(new ListOfRequestMethodRule(new GetMethod(), new PostMethod(), new PutMethod()),
				new RuleThatCanBeObserved<>(Arrays.asList("get", "post", "put"),
						Arrays.asList("delete", "head", "patch")));
	}
}
