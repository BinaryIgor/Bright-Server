package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.filter.RequestFilter;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.Response;

public class AuthorizationSecondFreePassFilter implements RequestFilter {

    @Override
    public Response filter(Request request) {
	System.out.println("Everyone can go!");
	return new EmptyResponse(ResponseCode.OK);
    }

}
