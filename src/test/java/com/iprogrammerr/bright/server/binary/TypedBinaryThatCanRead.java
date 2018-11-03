package com.iprogrammerr.bright.server.binary;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.binary.type.TypedBinary;

public final class TypedBinaryThatCanRead extends TypeSafeMatcher<TypedBinary> {

	private final File source;
	private final String type;

	public TypedBinaryThatCanRead(File source, String type) {
		this.source = source;
		this.type = type;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(TypedBinary item) {
		boolean matched;
		try {
			matched = this.type.equals(item.type());
			if (matched) {
				byte[] content = item.content();
				matched = content.length == this.source.length() && Arrays.equals(content,
						new PacketsBinary(new BufferedInputStream(new FileInputStream(this.source)),
								this.source.length()).content());
			}
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
