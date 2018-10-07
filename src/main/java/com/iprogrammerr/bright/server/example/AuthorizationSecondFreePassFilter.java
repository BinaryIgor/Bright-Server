package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.filter.Filter;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class AuthorizationSecondFreePassFilter implements Filter {

    @Override
    public Response response(Request request) {
	System.out.println("Everyone can go!");
	return new OkResponse();
    }

}
