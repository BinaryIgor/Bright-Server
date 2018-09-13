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
	if (fileType.equals("json")) {
	    return "application/" + fileType;
	}
	if (fileType.equals("js")) {
	    return "application/javascript";
	}
	if (image(fileType)) {
	    return "image/" + fileType;
	}
	return fileType;
    }

    private boolean image(String fileType) {
	return fileType.equals("jpg") || fileType.equals("png") || fileType.equals("svg") || fileType.equals("bmp")
		|| fileType.equals("ico") || fileType.equals("tif") || fileType.equals("tiff")
		|| fileType.equals("webp");
    }

}
