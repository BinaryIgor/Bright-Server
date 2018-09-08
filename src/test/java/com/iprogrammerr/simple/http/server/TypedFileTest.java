package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.BinaryFile;
import com.iprogrammerr.bright.server.binary.TypedFile;

public class TypedFileTest {

    @Test
    public void workingDirectory() throws Exception {
	File sourceFile = new File(TypedFileTest.class.getResource("/test.html").getFile());
	BinaryFile binaryFile = new TypedFile(sourceFile);
	assertTrue(binaryFile.type().equals("html"));
	byte[] content = binaryFile.content();
	assertEquals(content.length, sourceFile.length());
    }
}
