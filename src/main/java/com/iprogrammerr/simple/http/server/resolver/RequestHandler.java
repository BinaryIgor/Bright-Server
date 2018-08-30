package com.iprogrammerr.simple.http.server.resolver;

import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.response.Response;

public interface RequestHandler {
    Response handle(Request request);
}
