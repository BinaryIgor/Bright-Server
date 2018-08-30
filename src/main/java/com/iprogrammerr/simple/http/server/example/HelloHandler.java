package com.iprogrammerr.simple.http.server.example;

import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.resolver.RequestHandler;
import com.iprogrammerr.simple.http.server.response.PlainTextResponse;
import com.iprogrammerr.simple.http.server.response.Response;

public class HelloHandler implements RequestHandler {

    @Override
    public Response handle(Request request) {
	int id = request.getPathVariable("id", Integer.class);
	String message = "Hello number " + id + " one!";
	return new PlainTextResponse(ResponseCode.OK, message);
    }

}
