package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.request.MatchedRequest;
import com.iprogrammerr.bright.server.respondent.Respondent;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.BadRequestResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class ComplexUrlRespondent implements Respondent {

    @Override
    public Response response(MatchedRequest request) {
	try {
	    long id = request.pathVariable("id", Long.class);
	    String message = request.parameter("message", String.class);
	    float scale = request.parameter("scale", Float.class);
	    String mirror = String.format("id=%d, message=%s, scale=%.3f", id, message, scale);
	    return new OkResponse(mirror);
	} catch (Exception e) {
	    return new BadRequestResponse(e.getMessage());
	}
    }

}
