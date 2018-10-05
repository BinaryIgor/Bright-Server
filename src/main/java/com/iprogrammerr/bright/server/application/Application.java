package com.iprogrammerr.bright.server.application;

import java.util.Optional;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface Application {
    Optional<Response> response(Request request);
}
