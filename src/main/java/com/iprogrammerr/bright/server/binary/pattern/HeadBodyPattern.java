package com.iprogrammerr.bright.server.binary.pattern;

public final class HeadBodyPattern implements BinaryPattern {

	private static final String HEAD_BODY_SEPARATOR = "\r\n\r\n";
	private final BinaryPattern base;

	private HeadBodyPattern(BinaryPattern base) {
		this.base = base;
	}

	public HeadBodyPattern() {
		this(new ConfigurablePattern(HEAD_BODY_SEPARATOR.getBytes()));
	}

	@Override
	public byte[] value() {
		return this.base.value();
	}

	@Override
	public int index(byte[] content) {
		return this.base.index(content);
	}
}
