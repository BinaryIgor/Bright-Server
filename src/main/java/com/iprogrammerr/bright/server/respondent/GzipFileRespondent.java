package com.iprogrammerr.bright.server.respondent;

import com.iprogrammerr.bright.server.binary.processed.GzipCompressedBinary;
import com.iprogrammerr.bright.server.binary.type.HttpTypes;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.header.template.ContentEncodingHeader;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class GzipFileRespondent implements FileRespondent {

    private final HttpTypes types;

    public GzipFileRespondent(HttpTypes types) {
	this.types = types;
    }

    @Override
    public Response respond(TypedBinary file) {
	try {
	    GzipCompressedBinary compressedBinary = new GzipCompressedBinary(file.content());
	    return new OkResponse(new TypedResponseBody(types.type(file.type()), compressedBinary.content()),
		    new ContentEncodingHeader(compressedBinary.algorithm()));
	} catch (Exception exception) {
	    exception.printStackTrace();
	    return new NotFoundResponse();
	}
    }

}
