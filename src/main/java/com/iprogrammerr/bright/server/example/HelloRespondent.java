package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.request.MatchedRequest;
import com.iprogrammerr.bright.server.respondent.Respondent;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.BadRequestResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class HelloRespondent implements Respondent {

    @Override
    public Response response(MatchedRequest request) {
	try {
	    int id = request.pathVariable("id", Integer.class);
	    String message = "Hello number " + id;
	    return new OkResponse(message);
	} catch (Exception exception) {
	    return new BadRequestResponse(exception.getMessage());
	}
    }
}
