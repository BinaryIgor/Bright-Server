package com.iprogrammerr.simple.http.server;

import org.junit.Test;

import com.iprogrammerr.bright.server.exception.ReadingRequestException;

public class ToCheckSolutionTest {

    @Test
    public void exceptionTest() {
	try {
	    throw new ReadingRequestException("Superb!");
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }
}
