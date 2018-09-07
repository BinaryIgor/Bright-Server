package com.iprogrammerr.bright.server.configuration;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.request.Request;

public interface Cors {

    boolean validate(Request request);

    List<Header> toAddHeaders();
}
