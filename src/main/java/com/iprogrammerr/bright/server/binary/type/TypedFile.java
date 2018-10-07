package com.iprogrammerr.bright.server.binary.type;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.iprogrammerr.bright.server.binary.PacketsBinary;

public final class TypedFile implements TypedBinary {

    private final File file;

    public TypedFile(File file) {
	this.file = file;
    }

    @Override
    public byte[] content() throws Exception {
	try (InputStream is = new FileInputStream(this.file)) {
	    return new PacketsBinary(is, this.file.length()).content();
	}
    }

    @Override
    public String type() {
	String name = this.file.getName();
	int dotIndex = name.indexOf(".");
	if (dotIndex <= 0 || dotIndex == (name.length() - 1)) {
	    return "";
	}
	return name.substring(dotIndex + 1, name.length());
    }

}
