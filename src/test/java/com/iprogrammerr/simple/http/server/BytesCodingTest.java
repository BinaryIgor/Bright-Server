package com.iprogrammerr.simple.http.server;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class BytesCodingTest {

    @Test
    public void unexpectedLengthChange() throws UnsupportedEncodingException {
	String text = "text, which is very long by the way";
	System.out.println("Raw length = " + text.length());
	byte[] raw = text.getBytes();
	String doubleCrlf = "\r\n\r\n";
	StringBuilder builder = new StringBuilder();
	builder.append(new String(raw)).append(doubleCrlf).append(new String(raw));
	int rawLength = raw.length + doubleCrlf.length() + raw.length;
	String decoded = builder.toString();
	int decodedLangth = decoded.length();
    }
}
