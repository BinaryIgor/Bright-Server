package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.model.Primitives;

public interface MatchedRequest extends Request {

	Primitives parameters();

	Primitives pathVariables();
}
