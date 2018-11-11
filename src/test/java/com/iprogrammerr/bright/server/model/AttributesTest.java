package com.iprogrammerr.bright.server.model;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.bright.server.mock.MockedBinary;

public final class AttributesTest {

	@Test
	public void shouldBeTypeSafe() throws Exception {
		Map<String, Class> types = new HashMap<>();
		Map<String, Object> values = new HashMap<>();
		int id = 3;
		types.put("id", Integer.class);
		values.put("id", 3);
		String name = "abc";
		types.put("name", String.class);
		values.put("name", name);
		byte[] binary = new byte[] { -3, 4, 55, 100, 22 };
		types.put("binary", byte[].class);
		values.put("binary", binary);
		assertThat(new Attributes().put("id", id).put("name", name).put("binary", binary),
				new TypedMapThatHaveProperValues(types, values));
	}

	@Test
	public void shouldBeImmutable() throws Exception {
		TypedMap attributes = new Attributes().put("id", 2L).put("name", "name").put("binary",
				new byte[] { -3, 4, 55, 100, 22 });
		List<KeyValue> keyValues = attributes.keyValues();
		KeyValue item1 = new StringObject("name2", "name2");
		KeyValue item2 = new StringObject("id2", 3L);
		keyValues.add(item1);
		keyValues.add(item2);
		assertThat(attributes.keyValues(), Matchers.not(Matchers.containsInAnyOrder(item1, item2)));

	}

	@Test
	public void canOverrideValueAndType() throws Exception {
		List<String> keys = Arrays.asList("id", "name", "scale", "binary");
		TypedMap attributes = new Attributes().put(keys.get(0), 1).put(keys.get(1), "abc")
				.put("scale", 4.5).put("binary", new MockedBinary().content());
		assertThat(attributes, new TypedMapThatCanOverrideValuesAndTypes(keys));
	}
}
