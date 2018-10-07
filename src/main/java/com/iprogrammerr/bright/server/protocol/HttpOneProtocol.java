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

    private static final String CONNECTION = "Connection";
    private static final String CLOSE = "close";
    private static final String NEW_LINE = "\n";
    private static final String KEY_VALUE_SEPARATOR = ": ";
    private static final String HEADERS_BODY_PARSED_SEPARATOR = "\r";
    private static final int MIN_VALID_FIRST_LINE_LENGTH = 10;
    private static final int MIN_REQUEST_METHOD_LENGTH = 3;
    private static final String CRLF_CRLF = "\r\n\r\n";
    private static final String SEGMENTS_SEPARATOR = "/";
    private static final String HTTP = "HTTP";
    private static final String RESPONSE_CODE_PREFIX = "HTTP/1.1 ";
    private static final String CONTENT_LENGTH = "Content-Length";

    @Override
    public Request request(InputStream inputStream) throws Exception {
	Binary binary = new OnePacketBinary(inputStream);
	String[] requestLines = new String(binary.content()).split(NEW_LINE);
	if (requestLines.length < 1 || requestLines[0].length() < MIN_VALID_FIRST_LINE_LENGTH) {
	    throw new Exception("Request is empty");
	}
	String method = method(requestLines[0]);
	String path = path(requestLines[0]);
	List<Header> headers = headers(requestLines);
	int bodyBytes = bodyBytes(headers);
	Request request;
	if (bodyBytes == 0) {
	    request = new ParsedRequest(method, path, headers);
	} else {
	    byte[] bodyPart = requestLines[requestLines.length - 1].getBytes();
	    request = bodyPart.length >= bodyBytes ? new ParsedRequest(method, path, headers, bodyPart)
		    : new ParsedRequest(method, path, headers,
			    new PacketsBinary(binary, bodyPart, bodyBytes).content());
	}
	return request;
    }

    private List<Header> headers(String[] lines) throws Exception {
	List<Header> headers = new ArrayList<>();
	for (int i = 1; i < lines.length; i++) {
	    String line = lines[i];
	    if (line.equals(HEADERS_BODY_PARSED_SEPARATOR)) {
		break;
	    }
	    headers.add(header(line));
	}
	return headers;
    }

    private int bodyBytes(List<Header> headers) {
	int bodyBytes = 0;
	try {
	    for (Header header : headers) {
		if (header.is(CONTENT_LENGTH)) {
		    bodyBytes = Integer.parseInt(header.value());
		    break;
		}
	    }
	} catch (Exception e) {
	    bodyBytes = 0;
	}
	return bodyBytes;
    }

    private String method(String line) throws Exception {
	int separator = line.indexOf(SEGMENTS_SEPARATOR);
	if (separator <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new Exception("First request line is invalid");
	}
	return line.substring(0, separator - 1);
    }

    private String path(String line) throws Exception {
	int separator = line.indexOf(SEGMENTS_SEPARATOR);
	if (separator <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new Exception("First request line is invalid");
	}
	int http = line.indexOf(HTTP);
	if (http <= 2 * MIN_REQUEST_METHOD_LENGTH) {
	    throw new Exception("First request line is invalid");
	}
	return line.substring(separator + 1, http).trim();
    }

    private Header header(String header) throws Exception {
	String[] keyValue = header.split(KEY_VALUE_SEPARATOR);
	if (keyValue.length < 2) {
	    throw new Exception(keyValue[0] + " is not a proper header");
	}
	return new HttpHeader(keyValue[0].trim(), keyValue[1].trim());
    }

    @Override
    public void write(OutputStream outputStream, Response response) throws Exception {
	StringBuilder builder = new StringBuilder();
	builder.append(stringedResponseCode(response.code()));
	for (Header header : response.headers()) {
	    builder.append(NEW_LINE).append(header.writable());
	}
	outputStream.write(builder.toString().getBytes());
	if (response.hasBody()) {
	    outputStream.write(CRLF_CRLF.getBytes());
	    outputStream.write(response.body());
	}
    }

    private String stringedResponseCode(int responseCode) {
	return RESPONSE_CODE_PREFIX + responseCode;
    }

    @Override
    public boolean shouldClose(Request request) {
	boolean close;
	try {
	    close = !request.hasHeader(CONNECTION) || request.header(CONNECTION).equalsIgnoreCase(CLOSE);
	} catch (Exception e) {
	    close = true;
	}
	return close;
    }

}
