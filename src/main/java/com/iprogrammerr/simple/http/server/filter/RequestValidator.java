package com.iprogrammerr.simple.http.server.filter;

import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.model.Response;

public interface RequestValidator {
    boolean isValid(Request request, Response response);
}
