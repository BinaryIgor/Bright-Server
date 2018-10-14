package com.iprogrammerr.bright.server.binary;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.pattern.ConfigurablePattern;

public final class BinaryStringParsingPerformanceTest {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");

    @Test
    public void binaryShouldBeFasterThanString() throws Exception {
	System.out.println("BinaryStringParsingPerformanceTest.binaryShouldBeFasterThanString()");
	for (int i = 0; i < 5; i++) {
	    isBinaryFasterThanString();
	}
    }

    private void isBinaryFasterThanString() throws Exception {
	String pattern = "\r\n\r\n";
	byte[] message = messageWith(pattern);
	long start = System.nanoTime();
	new String(message).split(pattern);
	long end = System.nanoTime();
	long stringTime = end - start;
	ConfigurablePattern configurablePattern = new ConfigurablePattern(pattern.getBytes());
	start = System.nanoTime();
	configurablePattern.index(message);
	end = System.nanoTime();
	long binaryTime = end - start;
	assertTrue(stringTime > binaryTime);
	print(stringTime, binaryTime);
    }

    private void print(long stringTime, long binaryTime) {
	double dividedStringTime = stringTime / 1_000.0;
	String stringedStringTime;
	if (dividedStringTime > 1000) {
	    stringedStringTime = DECIMAL_FORMAT.format(dividedStringTime / 1000) + " ms";
	} else {
	    stringedStringTime = DECIMAL_FORMAT.format(dividedStringTime) + " us";
	}
	double dividedBinaryTime = binaryTime / 1_000.0;
	String stringedBinaryTime;
	if (dividedBinaryTime > 1000) {
	    stringedBinaryTime = DECIMAL_FORMAT.format(dividedBinaryTime / 1000) + " ms";
	} else {
	    stringedBinaryTime = DECIMAL_FORMAT.format(dividedBinaryTime) + " us";
	}
	System.out.println(
		String.format("String method took %s, whereas binary took %s", stringedStringTime, stringedBinaryTime));
    }

    private byte[] messageWith(String pattern) throws Exception {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	int headers = 5 + ((int) (100 * Math.random()));
	for (int i = 0; i < headers; i++) {
	    baos.write(("Very long header: very long " + i + " value\r\n").getBytes());
	}
	int bodySize = 100 + ((int) (1_000_000 * Math.random()));
	byte[] body = new byte[bodySize];
	for (int i = 0; i < body.length; i++) {
	    body[i] = (byte) (Math.random() * Byte.MAX_VALUE);
	}
	baos.write(pattern.getBytes());
	baos.write(body);
	return baos.toByteArray();
    }

}
