package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.model.TypedMap;

public interface MatchedRequest extends Request {

    TypedMap parameters();

    TypedMap pathVariables();
}
