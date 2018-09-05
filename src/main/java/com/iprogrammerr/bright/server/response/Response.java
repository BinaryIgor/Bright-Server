package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.header.HttpHeader;

//TODO remove enums dependency
public interface Response {

    int responseCode();

    List<HttpHeader> headers();
    
    boolean hadBody();

    byte[] body();
}
