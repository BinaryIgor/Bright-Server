package com.iprogrammerr.bright.server.example;

import com.iprogrammerr.bright.server.binary.processed.GzipCompressedBinary;
import com.iprogrammerr.bright.server.binary.type.HttpTypes;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.header.template.ContentEncodingHeader;
import com.iprogrammerr.bright.server.respondent.FileRespondent;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public class ExampleFileRespondent implements FileRespondent {

    private final static String TO_COMPRESS_TYPE = "pdf";
    private final HttpTypes staticTypes;
    private final HttpTypes audioTypes;

    public ExampleFileRespondent(HttpTypes staticTypes, HttpTypes audioTypes) {
	this.staticTypes = staticTypes;
	this.audioTypes = audioTypes;
    }

    @Override
    public Response response(TypedBinary file) {
	Response response;
	try {
	    String type = file.type();
	    if (!type.equals(TO_COMPRESS_TYPE)) {
		response = new OkResponse(new TypedResponseBody(httpType(type), file.content()));
	    } else {
		GzipCompressedBinary gcb = new GzipCompressedBinary(file.content());
		response = new OkResponse(new TypedResponseBody(httpType(type), gcb.content()),
			new ContentEncodingHeader(gcb.algorithm()));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    response = new NotFoundResponse();
	}
	return response;
    }

    private String httpType(String type) {
	if (this.staticTypes.isKnown(type)) {
	    return this.staticTypes.type(type);
	}
	return this.audioTypes.type(type);
    }

}
