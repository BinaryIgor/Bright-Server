package com.iprogrammerr.bright.server.binary;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.binary.processed.CompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.DecompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.DeflateDecompressedBinary;
import com.iprogrammerr.bright.server.binary.processed.GzipDecompressedBinary;

public final class BinaryThatCanCompressAndDecompress extends TypeSafeMatcher<CompressedBinary> {

	private final byte[] source;
	private final Class<? extends DecompressedBinary> decompressed;
	private final String algorithm;

	public BinaryThatCanCompressAndDecompress(byte[] source, Class<? extends DecompressedBinary> decompressed,
			String algorithm) {
		this.source = source;
		this.decompressed = decompressed;
		this.algorithm = algorithm;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected void describeMismatchSafely(CompressedBinary item, Description description) {
		description.appendText(String.format("%s that can not decompress source of %s size using %s algorithm",
				getClass().getSimpleName(), this.source.length, this.algorithm));
	}

	@Override
	protected boolean matchesSafely(CompressedBinary item) {
		boolean matched;
		try {
			byte[] compressed = item.content();
			DecompressedBinary db;
			if (this.decompressed.isAssignableFrom(DeflateDecompressedBinary.class)) {
				db = new DeflateDecompressedBinary(compressed, this.source.length);
			} else {
				db = new GzipDecompressedBinary(compressed, this.source.length);
			}
			matched = this.algorithm.equals(item.algorithm()) && this.algorithm.equals(db.algorithm())
					&& Arrays.equals(this.source, db.content());
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
