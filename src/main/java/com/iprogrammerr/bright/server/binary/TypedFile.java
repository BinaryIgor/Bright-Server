package com.iprogrammerr.bright.server.binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class TypedFile implements BinaryFile {

    private final File file;

    public TypedFile(File file) throws Exception {
	this.file = file;
    }

    @Override
    public byte[] content() throws Exception {
	try (InputStream inputStream = new FileInputStream(file)) {
	    return new ScatteredBinary(inputStream, new byte[0], file.length()).content();
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
