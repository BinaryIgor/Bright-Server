package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

//TODO create more useful templates
public interface Response {

    int responseCode();

    List<Header> headers();

    boolean hasBody();

    byte[] body();
}
