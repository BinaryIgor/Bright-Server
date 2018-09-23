package com.iprogrammerr.simple.http.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedFile;

public class TypedFileTest {

    @Test
    public void read() throws Exception {
	File sourceFile = new File(TypedFileTest.class.getResource("/test.html").getFile());
	TypedBinary binaryFile = new TypedFile(sourceFile);
	assertTrue(binaryFile.type().equals("html"));
	byte[] content = binaryFile.content();
	assertEquals(content.length, sourceFile.length());
    }
}
