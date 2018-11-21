package com.iprogrammerr.bright.server.model;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;

public final class AttributesTest {

	@Test
	public void shouldHaveProperValues() throws Exception {
		Map<String, Boolean> booleans = new HashMap<>();
		Map<String, Number> numbers = new HashMap<>();
		Map<String, String> strings = new HashMap<>();
		booleans.put("fast", true);
		numbers.put("id", 1L);
		numbers.put("scale", 44.4);
		numbers.put("error", -1);
		strings.put("secret", "");
		strings.put("message", "message");
		assertThat(new Attributes().put("fast", true).put("id", 1).put("scale", 44.4).put("error", -1).put("secret", "")
				.put("message", "message"), new PrimitivesMapThatHaveProperValues(booleans, numbers, strings));
	}

	@Test
	public void shouldBeImmutable() throws Exception {
		Primitives attributes = new Attributes().put("id", 2L).put("name", "name").put("fast", false);
		List<KeyValue> keyValues = attributes.keyValues();
		KeyValue item1 = new StringObject("name2", "name2");
		KeyValue item2 = new StringObject("id2", 3L);
		keyValues.add(item1);
		keyValues.add(item2);
		assertThat(attributes.keyValues(), Matchers.not(Matchers.containsInAnyOrder(item1, item2)));

	}

	@Test
	public void canOverrideValueAndType() throws Exception {
		List<String> keys = Arrays.asList("id", "name", "scale", "tempo");
		Primitives attributes = new Attributes().put(keys.get(0), 1).put(keys.get(1), "abc").put(keys.get(2), 4.5)
				.put(keys.get(3), true);
		assertThat(attributes, new PrimitivesMapThatCanOverrideValuesAndTypes(keys));
	}
}
