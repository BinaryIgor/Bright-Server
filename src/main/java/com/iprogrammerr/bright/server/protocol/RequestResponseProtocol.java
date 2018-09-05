package com.iprogrammerr.bright.server.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface RequestResponseProtocol {

    Request read(InputStream inputStream) throws IOException;

    void write(OutputStream outputStream, Response response) throws IOException;
}
