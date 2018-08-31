package com.iprogrammerr.bright.server.response;

import java.util.List;

import com.iprogrammerr.bright.server.model.Header;

public interface Response {

    int getResponseCode();

    List<Header> getHeaders();

    boolean hasBody();

    byte[] getBody();
}
