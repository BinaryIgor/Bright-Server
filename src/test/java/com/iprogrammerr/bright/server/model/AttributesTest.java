package com.iprogrammerr.bright.server.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.bright.server.exception.ToCatchException;

public final class AttributesTest {

	@Test
	public void shouldBeTypeSafe() throws Exception {
		int id = 3;
		String name = "name";
		byte[] binary = new byte[] { -3, 4, 55, 100, 22 };
		TypedMap attributes = new Attributes().put("id", id).put("name", name).put("binary",
				binary);
		assertTrue(id == attributes.intValue("id"));
		assertTrue(name.equals(attributes.stringValue("name")));
		assertTrue(Arrays.equals(binary, attributes.binaryValue("binary")));
		ToCatchException toCatch = new ToCatchException();
		assertTrue(toCatch.hasCatched(() -> attributes.longValue("id")));
		assertTrue(toCatch.hasCatched(() -> attributes.intValue("name")));
		assertTrue(toCatch.hasCatched(() -> attributes.floatValue("binary")));
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
