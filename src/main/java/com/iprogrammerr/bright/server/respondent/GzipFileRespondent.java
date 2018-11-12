package com.iprogrammerr.bright.server.respondent;

import com.iprogrammerr.bright.server.binary.processed.GzipCompressedBinary;
import com.iprogrammerr.bright.server.binary.type.HttpTypes;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.header.template.ContentEncodingHeader;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class GzipFileRespondent implements FileRespondent {

	private final HttpTypes types;

	public GzipFileRespondent(HttpTypes types) {
		this.types = types;
	}

	@Override
	public Response response(TypedBinary file) throws Exception {
		GzipCompressedBinary gcb = new GzipCompressedBinary(file.content());
		return new OkResponse(new TypedResponseBody(this.types.type(file.type()), gcb.content()),
				new ContentEncodingHeader(gcb.algorithm()));
	}
}
