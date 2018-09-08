package com.iprogrammerr.bright.server.binary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ScatteredBinary implements Binary {

    private final Binary binary;
    private final byte[] base;
    private long toRead;

    public ScatteredBinary(InputStream source, byte[] base, long toRead) {
	this(new OnePacketBinary(source), base, toRead);
    }

    public ScatteredBinary(InputStream source, long toRead) {
	this(new OnePacketBinary(source), new byte[0], toRead);
    }

    public ScatteredBinary(Binary binary, long toRead) {
	this(binary, new byte[0], toRead);
    }

    public ScatteredBinary(Binary binary, byte[] base, long toRead) {
	this.binary = binary;
	this.base = base;
	this.toRead = toRead;
    }

    @Override
    public byte[] content() throws Exception {
	List<byte[]> parts = new ArrayList<>();
	if (base.length > 0) {
	    parts.add(base);
	}
	long toReadBytes = toRead - base.length;
	if (toReadBytes < 1) {
	    return base;
	}
	long bytesRead = base.length;
	while (bytesRead != toRead) {
	    byte[] packet = binary.content();
	    parts.add(packet);
	    bytesRead += packet.length;
	}
	if (parts.size() == 1) {
	    return parts.get(0);
	}
	return concatenate(parts);
    }

    private byte[] concatenate(List<byte[]> toConcatenateBytes) throws IOException {
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	for (byte[] toConcatBytes : toConcatenateBytes) {
	    outputStream.write(toConcatBytes);
	}
	return outputStream.toByteArray();
    }

}
