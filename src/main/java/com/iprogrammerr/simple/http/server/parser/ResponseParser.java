package com.iprogrammerr.simple.http.server.parser;

import com.iprogrammerr.simple.http.server.configuration.ServerConfiguration;
import com.iprogrammerr.simple.http.server.constants.HeadersValues;
import com.iprogrammerr.simple.http.server.constants.ParserConstants;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.constants.ResponseHeaderKey;
import com.iprogrammerr.simple.http.server.model.Header;
import com.iprogrammerr.simple.http.server.model.Response;

public class ResponseParser {

    private ServerConfiguration serverConfiguration;

    public ResponseParser(ServerConfiguration serverConfiguration) {
	this.serverConfiguration = serverConfiguration;
    }

    public byte[] getResponse(Response response) {
	return getStringedResponse(response).getBytes();
    }

    private String getStringedResponse(Response response) {
	StringBuilder builder = new StringBuilder();
	builder.append(responseCodeToString(response.getCode())).append(ParserConstants.NEW_LINE_SEPARATOR)
		.append(ResponseHeaderKey.ACCESS_CONTROL_ALLOW_ORIGIN.getValue()).append(": ")
		.append(serverConfiguration.getAllowedOrigins());
	for (Header header : response.getHeaders()) {
	    builder.append(ParserConstants.NEW_LINE_SEPARATOR).append(header.getWritable());
	}
	if (!response.hasBody()) {
	    return builder.toString();
	}
	builder.append(ParserConstants.NEW_LINE_SEPARATOR).append(response.getContentTypeHeader().getWritable())
		.append(ParserConstants.NEW_LINE_SEPARATOR).append(response.getContentLengthHeader().getWritable())
		.append(ParserConstants.HEADERS_BODY_SEPARATOR);
	String contentType = response.getContentTypeHeader().getValue();
	if (contentType.equals(HeadersValues.JSON_CONTENT_TYPE)
		|| contentType.equals(HeadersValues.TEXT_PLAIN_CONTENT_TYPE)) {
	    builder.append(new String(response.getBody()));
	} else {
	    builder.append(response.getBody());
	}
	return builder.toString();
    }

    private String responseCodeToString(ResponseCode responseCode) {
	return "HTTP/1.1 " + responseCode.getValue();
    }

}
