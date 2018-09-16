package com.iprogrammerr.bright.server.respondent;

import com.iprogrammerr.bright.server.request.MatchedRequest;
import com.iprogrammerr.bright.server.response.Response;

public interface Respondent {
    Response respond(MatchedRequest request);
}
