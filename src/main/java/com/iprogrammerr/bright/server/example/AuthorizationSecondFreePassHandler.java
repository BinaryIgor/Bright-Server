package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.filter.ToFilterRequestHandler;
import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.Response;

public class AuthorizationSecondFreePassHandler implements ToFilterRequestHandler{

    @Override
    public Response handle(Request request) {
	System.out.println("Everyone can go!");
	return new EmptyResponse(ResponseCode.OK);
    }

}
