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
		GzipCompressedBinary compressedBinary = new GzipCompressedBinary(file.content());
		response = new OkResponse(new TypedResponseBody(httpType(type), compressedBinary.content()),
			new ContentEncodingHeader(compressedBinary.algorithm()));
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	    response = new NotFoundResponse();
	}
	return response;
    }

    private String httpType(String type) {
	if (staticTypes.isKnown(type)) {
	    return staticTypes.type(type);
	}
	return audioTypes.type(type);
    }

}
