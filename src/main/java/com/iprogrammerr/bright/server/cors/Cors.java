package com.iprogrammerr.bright.server.cors;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public interface Cors {

	boolean is(Request request);

	boolean isValid(Request request);

	List<Header> toAddHeaders();
}
