package com.iprogrammerr.simple.http.server.response;

import java.util.List;

import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.model.Header;

public interface Response {

    ResponseCode getResponseCode();

    List<Header> getHeaders();

    boolean hasBody();

    byte[] getBody();

    Header getContentTypeHeader();
}
