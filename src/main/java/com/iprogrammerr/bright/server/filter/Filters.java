package com.iprogrammerr.bright.server.filter;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.IntermediateResponse;

public interface Filters {
	IntermediateResponse response(Request request);
}
