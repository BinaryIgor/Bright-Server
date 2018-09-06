package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.header.TextPlainContentTypeHeader;
import com.iprogrammerr.bright.server.request.MatchedRequest;
import com.iprogrammerr.bright.server.respondent.Respondent;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class HelloRespondent implements Respondent {

    @Override
    public Response respond(MatchedRequest request) throws Exception {
	int id = request.pathVariable("id", Integer.class);
	String message = "Hello number " + id;
	return new OkResponse(new TextPlainContentTypeHeader(), message);
    }
}
