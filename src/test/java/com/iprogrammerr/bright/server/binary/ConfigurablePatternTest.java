package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.pattern.BinaryPattern;
import com.iprogrammerr.bright.server.binary.pattern.ConfigurablePattern;

public final class ConfigurablePatternTest {

    @Test
    public void canFindIndex() {
	String pattern = "\r\n";
	String message = "abcSecret" + pattern + "abcdef";
	byte[] rawMessage = message.getBytes();
	BinaryPattern binaryPattern = new ConfigurablePattern(pattern.getBytes());
	int index = binaryPattern.index(rawMessage);
	assertTrue(index > 0);
	String subString = new String(Arrays.copyOfRange(rawMessage, index, rawMessage.length));
	assertTrue(subString.startsWith(pattern));
    }

    @Test
    public void shouldNotFindIndex() {
	BinaryPattern pattern = new ConfigurablePattern("abc".getBytes());
	int index = pattern.index("zbdeSecreddba".getBytes());
	assertTrue(index == -1);
    }
}
