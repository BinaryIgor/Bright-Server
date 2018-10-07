package com.iprogrammerr.bright.server.binary;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class PacketsBinary implements Binary {

    private final Binary base;
    private final byte[] readPart;
    private final long toRead;

    public PacketsBinary(InputStream source, byte[] readPart, long toRead) {
	this(new OnePacketBinary(source), readPart, toRead);
    }

    public PacketsBinary(InputStream source, long toRead) {
	this(new OnePacketBinary(source), new byte[0], toRead);
    }

    public PacketsBinary(Binary base, long toRead) {
	this(base, new byte[0], toRead);
    }

    public PacketsBinary(Binary base, byte[] readPart, long toRead) {
	this.base = base;
	this.readPart = readPart;
	this.toRead = toRead;
    }

    @Override
    public byte[] content() throws Exception {
	List<byte[]> parts = new ArrayList<>();
	if (this.readPart.length > 0) {
	    parts.add(this.readPart);
	}
	long toRead = this.toRead - this.readPart.length;
	if (toRead < 1) {
	    throw new Exception(String.format("%d is not a proper bytes number to read", toRead));
	}
	long read = this.readPart.length;
	while (read != toRead) {
	    byte[] packet = this.base.content();
	    parts.add(packet);
	    read += packet.length;
	}
	byte[] concatenated;
	if (parts.size() == 1) {
	    concatenated = parts.get(0);
	} else {
	    concatenated = concatenated(parts);
	}
	return concatenated;
    }

    private byte[] concatenated(List<byte[]> parts) throws Exception {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	for (byte[] part : parts) {
	    baos.write(part);
	}
	return baos.toByteArray();
    }

}
