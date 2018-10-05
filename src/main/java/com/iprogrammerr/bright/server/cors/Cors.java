package com.iprogrammerr.bright.server.cors;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public interface Cors {

    boolean isValid(Request request);

    List<Header> toAddHeaders();
}
