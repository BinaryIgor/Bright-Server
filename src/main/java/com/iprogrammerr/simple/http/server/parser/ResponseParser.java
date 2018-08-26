package com.iprogrammerr.simple.http.server.parser;

import com.iprogrammerr.simple.http.server.configuration.ServerConfiguration;
import com.iprogrammerr.simple.http.server.constants.HeadersValues;
import com.iprogrammerr.simple.http.server.constants.ParserConstants;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.model.Header;
import com.iprogrammerr.simple.http.server.model.Response;

public class ResponseParser {

    private ServerConfiguration serverConfiguration;

    public ResponseParser(ServerConfiguration serverConfiguration) {
	this.serverConfiguration = serverConfiguration;
    }

    public byte[] getResponse(Response response) {
	StringBuilder builder = new StringBuilder();
	builder.append(responseCodeToString(response.getCode())).append(ParserConstants.NEW_LINE_SEPARATOR)
		.append(Header.createAccessControlAllowOriginsHeader(serverConfiguration.getAllowedOrigins()))
		.append(ParserConstants.NEW_LINE_SEPARATOR)
		.append(Header.createAccessControlAllowHeadersHeader(serverConfiguration.getAllowedHeaders()))
		.append(ParserConstants.NEW_LINE_SEPARATOR).append(Header.createCurrentDateHeader());
	for (Header header : response.getHeaders()) {
	    builder.append(ParserConstants.NEW_LINE_SEPARATOR).append(header);
	}
	if (!response.hasBody()) {
	    return builder.toString().getBytes();
	}
	builder.append(ParserConstants.NEW_LINE_SEPARATOR).append(response.getContentTypeHeader())
		.append(ParserConstants.NEW_LINE_SEPARATOR).append(response.getContentLengthHeader())
		.append(ParserConstants.HEADERS_BODY_SEPARATOR);
	String contentType = response.getContentTypeHeader().getValue();
	if (contentType.equals(HeadersValues.JSON_CONTENT_TYPE)
		|| contentType.equals(HeadersValues.TEXT_PLAIN_CONTENT_TYPE)) {
	    builder.append(new String(response.getBody()));
	} else {
	    builder.append(response.getBody());
	}
	return builder.toString().getBytes();
    }

    private String responseCodeToString(ResponseCode responseCode) {
	return ParserConstants.RESPONSE_CODE_HTTP_1_1_PREFIX + responseCode.getValue();
    }

}
