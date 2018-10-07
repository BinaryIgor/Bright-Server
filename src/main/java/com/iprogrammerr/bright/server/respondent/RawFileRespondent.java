package com.iprogrammerr.bright.server.respondent;

import com.iprogrammerr.bright.server.binary.type.HttpTypes;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class RawFileRespondent implements FileRespondent {

    private final HttpTypes types;

    public RawFileRespondent(HttpTypes types) {
	this.types = types;
    }

    @Override
    public Response response(TypedBinary file) {
	Response response;
	try {
	    response = new OkResponse(new TypedResponseBody(this.types.type(file.type()), file.content()));
	} catch (Exception e) {
	    e.printStackTrace();
	    response = new NotFoundResponse();
	}
	return response;
    }

}
