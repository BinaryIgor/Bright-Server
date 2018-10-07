package com.iprogrammerr.simple.http.server;

import org.junit.Test;

public class QuickTest {

    @Test
    public void canParse() {
	String toSplit = "dadaad";
	String[] split = toSplit.split("a");
	for (String s : split) {
	    System.out.println(s);
	}
    }
}
