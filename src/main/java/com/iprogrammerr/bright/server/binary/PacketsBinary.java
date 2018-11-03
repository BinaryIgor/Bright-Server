package com.iprogrammerr.bright.server.binary;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class PacketsBinary implements Binary {

	private final Binary base;
	private final byte[] readPart;
	private final long toRead;

	public PacketsBinary(Binary base, byte[] readPart, long toRead) {
		this.base = base;
		this.readPart = readPart;
		this.toRead = toRead;
	}

	public PacketsBinary(InputStream source, byte[] readPart, long toRead) {
		this(new OnePacketBinary(source), readPart, toRead);
	}

	public PacketsBinary(InputStream source, long toRead) {
		this(new OnePacketBinary(source), new byte[0], toRead);
	}

	public PacketsBinary(Binary base, long toRead) {
		this(base, new byte[0], toRead);
	}

	@Override
	public byte[] content() throws Exception {
		long toRead = this.toRead - this.readPart.length;
		if (toRead < 1) {
			throw new Exception(String.format("%d is not a proper bytes number to read", toRead));
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (this.readPart.length > 0) {
			baos.write(this.readPart);
		}
		long read = 0;
		while (read != toRead) {
			byte[] packet = this.base.content();
			baos.write(packet);
			read += packet.length;
		}
		return baos.toByteArray();
	}
}
