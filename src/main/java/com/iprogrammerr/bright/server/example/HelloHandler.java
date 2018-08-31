package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.model.ResolvedRequest;
import com.iprogrammerr.bright.server.resolver.RequestHandler;
import com.iprogrammerr.bright.server.response.PlainTextResponse;
import com.iprogrammerr.bright.server.response.Response;

public class HelloHandler implements RequestHandler {

    @Override
    public Response handle(ResolvedRequest request) {
	int id = request.getPathVariable("id", Integer.class);
	String message = "Hello number " + id + " one!";
	return new PlainTextResponse(ResponseCode.OK, message);
    }

}
