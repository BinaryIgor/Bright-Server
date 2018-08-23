package com.iprogrammerr.simpleserver.resolver;

import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

public interface RequestHandler {
    void handle(Request request, Response response);
}
