package com.iprogrammerr.simple.http.server.parser;

import com.iprogrammerr.simple.http.server.configuration.ServerConfiguration;
import com.iprogrammerr.simple.http.server.constants.RequestResponseConstants;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.constants.ResponseHeaderKey;
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
	builder.append(responseCodeToString(response.getCode())).append(RequestResponseConstants.NEW_LINE_SEPARATOR)
		.append(ResponseHeaderKey.ACCESS_CONTROL_ALLOW_ORIGIN.getValue()).append(": ")
		.append(serverConfiguration.getAllowedOrigins());
	if (response.hasBody()) {
	    builder.append(RequestResponseConstants.HEADERS_BODY_SEPARATOR).append(response.getBody());
	}
	return builder.toString();
    }

    private String responseCodeToString(ResponseCode responseCode) {
	return "HTTP/1.1 " + responseCode.getValue();
    }

}
