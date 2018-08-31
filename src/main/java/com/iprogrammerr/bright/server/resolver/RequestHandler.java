package com.iprogrammerr.bright.server.resolver;

import com.iprogrammerr.bright.server.model.ResolvedRequest;
import com.iprogrammerr.bright.server.response.Response;

public interface RequestHandler {
    Response handle(ResolvedRequest request);
}
