package com.iprogrammerr.simple.http.server.parser;

import java.io.IOException;
import java.io.InputStream;

import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.response.Response;

public interface RequestResponseParser {

    Request read(InputStream inputStream) throws IOException;

    byte[] write(Response response);
}
