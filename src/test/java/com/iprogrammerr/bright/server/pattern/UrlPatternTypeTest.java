package com.iprogrammerr.bright.server.pattern;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class UrlPatternTypeTest {

    @Test
    public void canProject() {
	UrlPatternType type = new UrlPatternType();
	assertTrue(Boolean.class.equals(type.type("boolean")));
	assertTrue(Integer.class.equals(type.type("int")));
	assertTrue(Long.class.equals(type.type("long")));
	assertTrue(Float.class.equals(type.type("float")));
	assertTrue(Double.class.equals(type.type("double")));
	assertTrue(String.class.equals(type.type("string")));
    }

    @Test
    public void canCastValues() throws Exception {
	UrlPatternType type = new UrlPatternType();
	assertTrue((boolean) type.value(Boolean.class, "true"));
	assertTrue(((int) type.value(Integer.class, "10")) == 10);
	assertTrue(((long) type.value(Long.class, "1033")) == 1033L);
	assertTrue(((float) type.value(Float.class, "10.33")) == 10.33f);
	assertTrue(((double) type.value(Double.class, "10.3344")) == 10.3344);
	assertTrue(((String) type.value(String.class, "secretMessage")).equals("secretMessage"));
    }

    @Test
    public void canProbeValues() {
	UrlPatternType type = new UrlPatternType();
	Double toProbeDouble = 3.33;
	Object probed = type.probedValue(toProbeDouble.toString());
	assertTrue(probed.getClass().isAssignableFrom(Double.class) && probed.equals(toProbeDouble));
	Integer toProbeInteger = 3;
	probed = type.probedValue(toProbeInteger.toString());
	assertTrue(probed.getClass().isAssignableFrom(Integer.class) && probed.equals(toProbeInteger));
	Long toProbeLong = ((long) (Integer.MAX_VALUE)) + 1L;
	probed = type.probedValue(toProbeLong.toString());
	assertTrue(probed.getClass().isAssignableFrom(Long.class) && probed.equals(toProbeLong));
	Boolean toProbeBoolean = true;
	probed = type.probedValue(toProbeBoolean.toString());
	assertTrue(probed.getClass().isAssignableFrom(Boolean.class) && probed.equals(toProbeBoolean));
	String toProbeString = "abcd";
	probed = type.probedValue(toProbeString);
	assertTrue(probed.getClass().isAssignableFrom(String.class) && probed.equals(toProbeString));
    }
}
