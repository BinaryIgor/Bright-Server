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
	try (InputStream inputStream = new FileInputStream(file)) {
	    return new PacketsBinary(inputStream, file.length()).content();
	} catch (Exception exception) {
	    throw exception;
	}
    }

    @Override
    public String type() {
	String fileName = file.getName();
	int indexOfDot = fileName.indexOf(".") + 1;
	if (indexOfDot == 0 || indexOfDot == (fileName.length() - 1)) {
	    return "";
	}
	return fileName.substring(indexOfDot, fileName.length());
    }

}
