package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface ConditionalFilter {

    boolean isPrimary();

    boolean areConditionsMet(Request request);

    Response filtered(Request request) throws Exception;
}
