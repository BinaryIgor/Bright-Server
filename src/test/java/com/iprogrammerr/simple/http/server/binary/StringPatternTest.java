package com.iprogrammerr.simple.http.server.binary;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.Pattern;
import com.iprogrammerr.bright.server.binary.StringPattern;

public final class StringPatternTest {

    @Test
    public void canFindIndex() {
	String pattern = "\r\n";
	String message = "abcSecret" + pattern + "abcdef";
	byte[] rawMessage = message.getBytes();
	Pattern stringPattern = new StringPattern(pattern);
	int index = stringPattern.index(rawMessage);
	assertTrue(index > 0);
	String subString = new String(Arrays.copyOfRange(rawMessage, index, rawMessage.length));
	assertTrue(subString.startsWith(pattern));
    }

    @Test
    public void shouldNotFindIndex() {
	Pattern pattern = new StringPattern("abc");
	int index = pattern.index("zbdeSecreddba".getBytes());
	assertTrue(index == -1);
    }
}
