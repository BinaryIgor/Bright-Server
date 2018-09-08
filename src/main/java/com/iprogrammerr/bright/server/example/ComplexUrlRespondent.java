package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.request.MatchedRequest;
import com.iprogrammerr.bright.server.respondent.Respondent;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class ComplexUrlRespondent implements Respondent {

    @Override
    public Response respond(MatchedRequest request) throws Exception {
	long id = request.pathVariable("id", Long.class);
	String message = request.parameter("message", String.class);
	float scale = request.parameter("scale", Float.class);
	Thread.sleep((long) (Math.random() * 500));
	return new OkResponse();
    }

}
