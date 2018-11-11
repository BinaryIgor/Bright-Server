package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public final class UrlPatternTypeTest {

	@Test
	public void canValue() {
		Map<Class, String> typesValues = new HashMap<>();
		typesValues.put(Integer.class, "1");
		typesValues.put(String.class, "name");
		typesValues.put(Double.class, "4.45");
		Map<String, Object> values = new HashMap<>();
		values.put("1", 1);
		values.put("name", "name");
		values.put("4.45", 4.45);
		assertThat(new UrlPatternType(), new TypeThatCanInferTypesAndValue(typesValues, values));
	}

	@Test
	public void canProbeValues() {
		assertThat(new UrlPatternType(),
				new TypeThatCanProbeValues(Arrays.asList(55.5, "name", true, 6)));
	}
}
