package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.request.MatchedRequest;
import com.iprogrammerr.bright.server.respondent.Respondent;
import com.iprogrammerr.bright.server.response.BadRequestResponse;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class HelloRespondent implements Respondent {

    @Override
    public Response respond(MatchedRequest request) {
	try {
	    int id = request.pathVariable("id", Integer.class);
	    String message = "Hello number " + id;
	    return new OkResponse(message);
	} catch (Exception exception) {
	    return new BadRequestResponse(exception.getMessage());
	}
    }
}
