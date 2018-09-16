package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface ConditionalRequestFilter {

    boolean primary();

    boolean conditionsMet(Request request);

    Response filter(Request request) throws Exception;
}
