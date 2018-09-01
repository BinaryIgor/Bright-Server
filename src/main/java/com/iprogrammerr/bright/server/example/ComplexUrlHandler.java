package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.model.ResolvedRequest;
import com.iprogrammerr.bright.server.resolver.RequestHandler;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.Response;

public class ComplexUrlHandler implements RequestHandler{

    @Override
    public Response handle(ResolvedRequest request) {
	long id = request.getPathVariable("id", Long.class);
	String message = request.getParameter("message", String.class);
	float scale = request.getParameter("scale", Float.class);
	System.out.println("id = " + id + " , message = " + message + ", scale = " + scale);
	return new EmptyResponse(ResponseCode.OK);
    }

}
