package com.iprogrammerr.bright.server.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

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
		assertTrue(keyValues.size() == 3);
		keyValues.add(new StringObject("name2", "name2"));
		keyValues.add(new StringObject("id2", 3L));
		assertFalse(attributes.has("name2", String.class));
		assertFalse(attributes.has("id2", Long.class));
		assertTrue(attributes.keyValues().size() == 3);
	}

	@Test
	public void canOverrideValueAndType() throws Exception {
		TypedMap attributes = new Attributes().put("id", 1);
		assertTrue(attributes.intValue("id") == 1);
		attributes.put("id", 33L);
		assertTrue(attributes.size() == 1);
		assertTrue(attributes.longValue("id") == 33);
		attributes.put("id", 0.33f);
		assertTrue(attributes.size() == 1);
		assertTrue(attributes.floatValue("id") == 0.33f);
	}

}
