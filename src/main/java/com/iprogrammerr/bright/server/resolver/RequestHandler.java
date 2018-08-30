package com.iprogrammerr.bright.server.resolver;

import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface RequestHandler {
    Response handle(Request request);
}
