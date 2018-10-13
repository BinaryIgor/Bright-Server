package com.iprogrammerr.simple.http.server.binary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedFile;

public class TypedFileTest {

    @Test
    public void canRead() throws Exception {
	File file = new File(TypedFileTest.class.getResource("/test.html").getFile());
	TypedBinary tb = new TypedFile(file);
	assertTrue(tb.type().equals("html"));
	byte[] content = tb.content();
	assertEquals(content.length, file.length());
    }
}
