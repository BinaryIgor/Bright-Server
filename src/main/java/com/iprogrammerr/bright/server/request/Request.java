package com.iprogrammerr.bright.server.request;

import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public interface Request {

    String url();

    String method();

    List<Header> headers();

    boolean hasHeader(String key);

    String header(String key) throws Exception;

    byte[] body();

    void removeContext(String context);
}
