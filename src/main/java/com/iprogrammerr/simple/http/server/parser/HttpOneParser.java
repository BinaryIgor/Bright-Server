package com.iprogrammerr.simple.http.server.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simple.http.server.configuration.ServerConfiguration;
import com.iprogrammerr.simple.http.server.constants.RequestHeaderKey;
import com.iprogrammerr.simple.http.server.constants.ResponseCode;
import com.iprogrammerr.simple.http.server.constants.ResponseHeaderKey;
import com.iprogrammerr.simple.http.server.exception.RequestException;
import com.iprogrammerr.simple.http.server.model.Header;
import com.iprogrammerr.simple.http.server.model.Request;
import com.iprogrammerr.simple.http.server.response.Response;

public class HttpOneParser implements RequestResponseParser {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private static final String HEADERS_BODY_PARSED_SEPARATOR = "\r";
    private static final int MIN_VALID_FIRST_LINE_LENGTH = 10;
    private static final int MIN_REQUEST_METHOD_LENGTH = 3;
    private static final String HEADERS_BODY_SEPARATOR = "\r\n\r\n";
    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private static final String HTTP = "HTTP";
    private static final String RESPONSE_CODE_HTTP_1_1_PREFIX = "HTTP/1.1 ";
    private static final String JSON_CONTENT_TYPE = "Application/json";
    private static final String TEXT_PLAIN_CONTENT_TYPE = "text/plain";
    private ServerConfiguration serverConfiguration;

    public HttpOneParser(ServerConfiguration serverConfiguration) {
	this.serverConfiguration = serverConfiguration;
    }

    @Override
    public Request read(InputStream inputStream) throws IOException {
	String[] requestLines = readRequest(inputStream);
	if (requestLines.length < 1 || requestLines[0].length() < MIN_VALID_FIRST_LINE_LENGTH) {
	    throw new RequestException();
	}
	String method = getMethod(requestLines[0]);
	String path = getPath(requestLines[0]);
	List<Header> headers = new ArrayList<>();
	int i;
	for (i = 1; i < requestLines.length; i++) {
	    String line = requestLines[i];
	    if (line.isEmpty() || line.equals(HEADERS_BODY_PARSED_SEPARATOR)) {
		break;
	    }
	    headers.add(getHeader(line));
	}
	byte[] body = new byte[0];
	if ((i + 1) < requestLines.length && requestLines[i].equals(HEADERS_BODY_PARSED_SEPARATOR)) {
	    body = requestLines[i + 1].getBytes();
	}
	return new Request(method, path, headers, body);
    }

    private String[] readRequest(InputStream inputStream) throws IOException {
	BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
	int availableBytes = bufferedInputStream.available();
	byte[] bytes = new byte[availableBytes];
	bufferedInputStream.read(bytes);
	String request = new String(bytes);
	String[] lines = request.split(NEW_LINE_SEPARATOR);
	return lines;
    }

    private String getMethod(String firstLine) {
	int indexOfSeparator = firstLine.indexOf("/");
	if (indexOfSeparator <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	return firstLine.substring(0, indexOfSeparator - 1);
    }

    private String getPath(String firstLine) {
	int indexOfSeparator = firstLine.indexOf(URL_SEGMENTS_SEPARATOR);
	if (indexOfSeparator <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	int indexOfHttp = firstLine.indexOf(HTTP);
	if (indexOfHttp <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	return firstLine.substring(indexOfSeparator + 1, indexOfHttp).trim();
    }

    private Header getHeader(String headerToParse) {
	String[] keyValue = headerToParse.split(HEADER_KEY_VALUE_SEPARATOR);
	if (keyValue.length < 2) {
	    throw new RequestException();
	}
	return new Header(keyValue[0].trim(), keyValue[1].trim());
    }

    @Override
    public byte[] write(Response response) {
	StringBuilder builder = new StringBuilder();
	Header accessControlAllowedHeadersHeader = new Header(ResponseHeaderKey.ACCESS_CONTROL_ALLOW_HEADERS,
		serverConfiguration.getAllowedHeaders());
	Header accessControllAllowedMethodsHeader = new Header(ResponseHeaderKey.ACCESS_CONTROL_ALLOW_METHODS,
		serverConfiguration.getAllowedMethods());
	Header accessControllAllowedOriginsHeader = new Header(ResponseHeaderKey.ACCESS_CONTROL_ALLOW_ORIGIN,
		serverConfiguration.getAllowedOrigins());
	LocalDateTime currentDate = LocalDateTime.now(ZoneOffset.UTC);
	Header currentDateHeader = new Header(ResponseHeaderKey.DATE, currentDate.toString());
	builder.append(responseCodeToString(response.getResponseCode())).append(NEW_LINE_SEPARATOR)
		.append(accessControlAllowedHeadersHeader).append(NEW_LINE_SEPARATOR)
		.append(accessControllAllowedMethodsHeader).append(NEW_LINE_SEPARATOR)
		.append(accessControllAllowedOriginsHeader).append(NEW_LINE_SEPARATOR).append(currentDateHeader);
	for (Header header : response.getHeaders()) {
	    builder.append(NEW_LINE_SEPARATOR).append(header);
	}
	if (!response.hasBody()) {
	    return builder.toString().getBytes();
	}
	builder.append(NEW_LINE_SEPARATOR).append(response.getContentTypeHeader()).append(NEW_LINE_SEPARATOR)
		.append(new Header(RequestHeaderKey.CONTENT_LENGTH, String.valueOf(response.getBody().length)))
		.append(HEADERS_BODY_SEPARATOR);
	String contentType = response.getContentTypeHeader().getValue();
	if (contentType.equals(JSON_CONTENT_TYPE) || contentType.equals(TEXT_PLAIN_CONTENT_TYPE)) {
	    builder.append(new String(response.getBody()));
	} else {
	    builder.append(response.getBody());
	}
	return builder.toString().getBytes();
    }

    private String responseCodeToString(ResponseCode responseCode) {
	return RESPONSE_CODE_HTTP_1_1_PREFIX + responseCode.getValue();
    }

}