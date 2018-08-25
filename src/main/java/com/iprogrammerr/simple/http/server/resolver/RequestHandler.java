package com.iprogrammerr.simple.http.server.resolver;

import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.model.Response;

public interface RequestHandler {
    void handle(Request request, Response response);
}
