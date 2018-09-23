package com.iprogrammerr.bright.server.respondent;

import com.iprogrammerr.bright.server.binary.type.HttpTypes;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class SimpleFileRespondent implements FileRespondent {

    private final HttpTypes types;

    public SimpleFileRespondent(HttpTypes types) {
	this.types = types;
    }

    @Override
    public Response respond(TypedBinary file) {
	try {
	    return new OkResponse(new TypedResponseBody(types.type(file.type()), file.content()));
	} catch (Exception exception) {
	    exception.printStackTrace();
	    return new NotFoundResponse();
	}
    }

}
