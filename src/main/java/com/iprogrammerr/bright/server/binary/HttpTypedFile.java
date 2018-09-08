package com.iprogrammerr.bright.server.binary;

import java.io.File;

//TODO all types
public class HttpTypedFile implements BinaryFile {

    private final BinaryFile binaryFile;

    public HttpTypedFile(BinaryFile binaryFile) {
	this.binaryFile = binaryFile;
    }

    public HttpTypedFile(File file) {
	this(new TypedFile(file));
    }

    @Override
    public byte[] content() throws Exception {
	return binaryFile.content();
    }

    @Override
    public String type() {
	String fileType = binaryFile.type();
	if (fileType.equals("html") || fileType.equals("css")) {
	    return "text/" + fileType;
	}
	if (fileType.equals("txt")) {
	    return "text/plain";
	}
	if (fileType.equals("json") || fileType.equals("js")) {
	    return "application/" + fileType;
	}
	return fileType;
    }

}
