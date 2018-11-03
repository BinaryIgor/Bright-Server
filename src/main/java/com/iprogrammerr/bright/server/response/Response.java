package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public interface Response {

	int code();

	List<Header> headers();

	byte[] body();
}
