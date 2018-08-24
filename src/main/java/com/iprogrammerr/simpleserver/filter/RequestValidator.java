package com.iprogrammerr.simpleserver.filter;

import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

public interface RequestValidator {
    boolean isValid(Request request, Response response);
}
