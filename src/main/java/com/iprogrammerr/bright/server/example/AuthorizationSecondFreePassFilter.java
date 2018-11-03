package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.filter.Filter;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.IntermediateResponse;
import com.iprogrammerr.bright.server.response.ToForwardResponse;

public final class AuthorizationSecondFreePassFilter implements Filter {

	@Override
	public IntermediateResponse response(Request request) {
		System.out.println("Everyone can go!");
		return new ToForwardResponse();
	}
}
