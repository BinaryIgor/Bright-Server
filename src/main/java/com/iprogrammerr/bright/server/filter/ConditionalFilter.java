package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.IntermediateResponse;

public interface ConditionalFilter {

	boolean isPrimary();

	boolean areConditionsMet(Request request);

	IntermediateResponse response(Request request) throws Exception;
}
