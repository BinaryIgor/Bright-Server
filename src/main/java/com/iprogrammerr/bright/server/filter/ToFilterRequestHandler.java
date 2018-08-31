package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface ToFilterRequestHandler {
    Response handle(Request request);
}
