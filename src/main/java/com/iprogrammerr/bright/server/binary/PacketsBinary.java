package com.iprogrammerr.bright.server.binary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class PacketsBinary implements Binary {

    private final Binary base;
    private final byte[] read;
    private final long toRead;

    public PacketsBinary(InputStream source, byte[] read, long toRead) {
	this(new OnePacketBinary(source), read, toRead);
    }

    public PacketsBinary(InputStream source, long toRead) {
	this(new OnePacketBinary(source), new byte[0], toRead);
    }

    public PacketsBinary(Binary base, long toRead) {
	this(base, new byte[0], toRead);
    }

    public PacketsBinary(Binary base, byte[] read, long toRead) {
	this.base = base;
	this.read = read;
	this.toRead = toRead;
    }

    @Override
    public byte[] content() throws Exception {
	List<byte[]> parts = new ArrayList<>();
	if (read.length > 0) {
	    parts.add(read);
	}
	long toReadBytes = toRead - read.length;
	if (toReadBytes < 1) {
	    throw new Exception(String.format("%d is not proper bytes number to read", toReadBytes));
	}
	long bytesRead = read.length;
	while (bytesRead != toRead) {
	    byte[] packet = base.content();
	    parts.add(packet);
	    bytesRead += packet.length;
	}
	byte[] concatenated;
	if (parts.size() == 1) {
	    concatenated = parts.get(0);
	} else {
	    concatenated = concatenate(parts);
	}
	return concatenated;
    }

    private byte[] concatenate(List<byte[]> toConcatenate) throws IOException {
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	for (byte[] bytes : toConcatenate) {
	    outputStream.write(bytes);
	}
	return outputStream.toByteArray();
    }

}
