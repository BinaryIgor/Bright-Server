package com.iprogrammerr.bright.server.parser;

import java.io.IOException;
import java.io.InputStream;

import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface RequestResponseParser {

    Request read(InputStream inputStream) throws IOException;

    byte[] write(Response response);
}
