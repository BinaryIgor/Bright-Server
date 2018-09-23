package com.iprogrammerr.bright.server.protocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.binary.Binary;
import com.iprogrammerr.bright.server.binary.OnePacketBinary;
import com.iprogrammerr.bright.server.binary.PacketsBinary;
import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.HttpHeader;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public class HttpOneProtocol implements RequestResponseProtocol {

    private static final String CONNECTION_HEADER = "Connection";
    private static final String CONNECTION_CLOSE = "close";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";
    private static final String HEADERS_BODY_PARSED_SEPARATOR = "\r";
    private static final int MIN_VALID_FIRST_LINE_LENGTH = 10;
    private static final int MIN_REQUEST_METHOD_LENGTH = 3;
    private static final String CRLF_CRLF = "\r\n\r\n";
    private static final String URL_SEGMENTS_SEPARATOR = "/";
    private static final String HTTP = "HTTP";
    private static final String RESPONSE_CODE_HTTP_1_1_PREFIX = "HTTP/1.1 ";
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";

    @Override
    public Request read(InputStream inputStream) throws Exception {
	Binary binary = new OnePacketBinary(inputStream);
	String[] requestLines = new String(binary.content()).split(NEW_LINE_SEPARATOR);
	if (requestLines.length < 1 || requestLines[0].length() < MIN_VALID_FIRST_LINE_LENGTH) {
	    throw new Exception("Request is empty");
	}
	String method = readMethod(requestLines[0]);
	String path = readPath(requestLines[0]);
	List<Header> headers = readHeaders(requestLines);
	int bodyBytes = bodyBytes(headers);
	if (bodyBytes == 0) {
	    return new ParsedRequest(method, path, headers);
	}
	byte[] partOfTheBody = requestLines[requestLines.length - 1].getBytes();
	if (partOfTheBody.length == bodyBytes) {
	    return new ParsedRequest(method, path, headers, partOfTheBody);
	}
	byte[] body = new PacketsBinary(binary, partOfTheBody, bodyBytes).content();
	return new ParsedRequest(method, path, headers, body);
    }

    private List<Header> readHeaders(String[] requestLines) throws Exception {
	List<Header> headers = new ArrayList<>();
	for (int i = 1; i < requestLines.length; i++) {
	    String line = requestLines[i];
	    if (line.equals(HEADERS_BODY_PARSED_SEPARATOR)) {
		break;
	    }
	    headers.add(readHeader(line));
	}
	return headers;
    }

    private int bodyBytes(List<Header> headers) {
	try {
	    int bodyBytes = 0;
	    for (Header header : headers) {
		if (header.is(CONTENT_LENGTH_HEADER)) {
		    bodyBytes = Integer.parseInt(header.value());
		    break;
		}
	    }
	    return bodyBytes > 0 ? bodyBytes : 0;
	} catch (NumberFormatException exception) {
	    return 0;
	}
    }

    private String readMethod(String firstLine) throws Exception {
	int indexOfSeparator = firstLine.indexOf("/");
	if (indexOfSeparator <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new Exception("First request line is invalid");
	}
	return firstLine.substring(0, indexOfSeparator - 1);
    }

    private String readPath(String firstLine) throws Exception {
	int indexOfSeparator = firstLine.indexOf(URL_SEGMENTS_SEPARATOR);
	if (indexOfSeparator <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new Exception("First request line is invalid");
	}
	int indexOfHttp = firstLine.indexOf(HTTP);
	if (indexOfHttp <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new Exception("First request line is invalid");
	}
	return firstLine.substring(indexOfSeparator + 1, indexOfHttp).trim();
    }

    private Header readHeader(String headerToParse) throws Exception {
	String[] keyValue = headerToParse.split(HEADER_KEY_VALUE_SEPARATOR);
	if (keyValue.length < 2) {
	    throw new Exception(keyValue[0] + " is not a proper header");
	}
	return new HttpHeader(keyValue[0].trim(), keyValue[1].trim());
    }

    @Override
    public void write(OutputStream outputStream, Response response) throws Exception {
	StringBuilder builder = new StringBuilder();
	builder.append(responseCodeToString(response.code()));
	for (Header header : response.headers()) {
	    builder.append(NEW_LINE_SEPARATOR).append(header.writable());
	}
	outputStream.write(builder.toString().getBytes());
	if (response.hasBody()) {
	    outputStream.write(CRLF_CRLF.getBytes());
	    outputStream.write(response.body());
	}
    }

    private String responseCodeToString(int responseCode) {
	return RESPONSE_CODE_HTTP_1_1_PREFIX + responseCode;
    }

    @Override
    public boolean closeConnection(Request request) {
	boolean close;
	try {
	    close = !request.hasHeader(CONNECTION_HEADER)
		    || request.header(CONNECTION_HEADER).equalsIgnoreCase(CONNECTION_CLOSE);
	} catch (Exception exception) {
	    close = true;
	}
	return close;
    }

}
