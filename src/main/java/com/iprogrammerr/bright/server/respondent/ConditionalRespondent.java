package com.iprogrammerr.bright.server.respondent;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public interface ConditionalRespondent {

    boolean canRespond(Request request);

    Response respond(Request request);
}
