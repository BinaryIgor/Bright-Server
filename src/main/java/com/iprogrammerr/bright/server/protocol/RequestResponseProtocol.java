package com.iprogrammerr.bright.server.protocol;

import java.io.InputStream;
import java.io.OutputStream;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface RequestResponseProtocol {

    Request request(InputStream inputStream) throws Exception;

    void write(OutputStream outputStream, Response response) throws Exception;

    boolean shouldClose(Request request);
}
