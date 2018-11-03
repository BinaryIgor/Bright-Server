package com.iprogrammerr.bright.server.binary;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.binary.pattern.BinaryPattern;

public final class BinaryPatternThatIsFasterThanStringParsing extends TypeSafeMatcher<BinaryPattern> {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");
	private final int tests;
	private final boolean print;

	public BinaryPatternThatIsFasterThanStringParsing(int tests, boolean print) {
		this.tests = tests;
		this.print = print;
	}

	public BinaryPatternThatIsFasterThanStringParsing() {
		this(10, true);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(BinaryPattern item) {
		boolean matched = true;
		try {
			for (int i = 0; i < this.tests; ++i) {
				byte[] pattern = item.value();
				byte[] message = messageWith(pattern);
				String toSplit = new String(message);
				long start = System.nanoTime();
				toSplit.split(new String(pattern));
				long end = System.nanoTime();
				long stringTime = end - start;
				start = System.nanoTime();
				item.index(message);
				end = System.nanoTime();
				long binaryTime = end - start;
				matched = stringTime > binaryTime;
				if (this.print) {
					print(stringTime, binaryTime);
				}
				if (!matched) {
					break;
				}
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
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
		System.out.println(String.format("String method took %s, whereas binary took %s",
				stringedStringTime, stringedBinaryTime));
	}

	private byte[] messageWith(byte[] pattern) throws Exception {
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
		baos.write(pattern);
		baos.write(body);
		return baos.toByteArray();
	}
}
