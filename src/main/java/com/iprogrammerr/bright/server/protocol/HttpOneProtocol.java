package com.iprogrammerr.bright.server.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.configuration.ServerConfiguration;
import com.iprogrammerr.bright.server.exception.RequestException;
import com.iprogrammerr.bright.server.header.DateHeader;
import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.HttpHeader;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;

public class HttpOneProtocol implements RequestResponseProtocol {

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
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private List<Header> additionalResponseHeaders;

    
    
    public HttpOneProtocol(ServerConfiguration serverConfiguration, List<Header> additionalHeaders) {
	this.additionalResponseHeaders = additionalHeaders;
	if (serverConfiguration.addCorsHeaders()) {
	    additionalResponseHeaders.addAll(serverConfiguration.corsHeaders());
	}
    }
    
    public HttpOneProtocol(ServerConfiguration serverConfiguration) {
	this(serverConfiguration, new ArrayList<>());
    }
    
    public HttpOneProtocol(List<Header> additionalResponseHeaders) {
	this.additionalResponseHeaders = additionalResponseHeaders;
    }

    @Override
    public Request read(InputStream inputStream) throws IOException {
	String[] requestLines = readRequest(inputStream);
	if (requestLines.length < 1 || requestLines[0].length() < MIN_VALID_FIRST_LINE_LENGTH) {
	    throw new RequestException("Request is empty");
	}
	String method = readMethod(requestLines[0]);
	String path = readPath(requestLines[0]);
	List<Header> headers = new ArrayList<>();
	int i;
	int bodyBytes = 0;
	for (i = 1; i < requestLines.length; i++) {
	    String line = requestLines[i];
	    if (line.isEmpty() || line.equals(HEADERS_BODY_PARSED_SEPARATOR)) {
		break;
	    }
	    Header header = readHeader(line);
	    if (header.is(CONTENT_LENGTH_HEADER_KEY)) {
		bodyBytes = bodyLength(header);
	    }
	    headers.add(header);
	}
	if (bodyBytes == 0) {
	    return new ParsedRequest(method, path, headers);
	}
	byte[] partOfTheBody;
	if ((i + 1) < requestLines.length) {
	    partOfTheBody = requestLines[i + 1].getBytes();
	} else {
	    partOfTheBody = new byte[0];
	}
	if (partOfTheBody.length == bodyBytes) {
	    return new ParsedRequest(method, path, headers, partOfTheBody);
	}
	byte[] body = readBody(inputStream, partOfTheBody, bodyBytes);
	return new ParsedRequest(method, path, headers, body);
    }
    
    private int bodyLength(Header header) {
	try {
	    int bodyLength = Integer.parseInt(header.value());
	    return bodyLength > 0 ? bodyLength : 0;
	} catch (NumberFormatException exception) {
	    return  0;
	}
    }


    private String[] readRequest(InputStream inputStream) throws IOException {
	byte[] rawRequest = readPacket(inputStream);
	String request = new String(rawRequest);
	return request.split(NEW_LINE_SEPARATOR);
    }
    
    private byte[] readBody(InputStream inputStream, byte[] partOfTheBody, int bodyBytes) throws IOException{
	List<byte[]> bodyParts = new ArrayList<>();
	if (partOfTheBody.length > 0) {
	    bodyParts.add(partOfTheBody);
	}
	int toReadBytes = bodyBytes - partOfTheBody.length;
	if (toReadBytes < 1) {
	    return partOfTheBody;
	}
	int bytesRead = partOfTheBody.length;
	while (bytesRead != bodyBytes) {
	    byte[] packet = readPacket(inputStream);
	    bodyParts.add(packet);
	    bytesRead += packet.length;
	}
	if (bodyParts.size() == 1) {
	    return bodyParts.get(0);
	}
	return concatenateBytes(bodyParts);
    }

    private byte[] readPacket(InputStream inputStream) throws IOException {
	int bytesAvailable = inputStream.available();
	if (bytesAvailable == 0) {
	    bytesAvailable = 1024;
	}
	byte[] buffer = new byte[bytesAvailable];
	int bytesRead = inputStream.read(buffer);
	if (bytesRead <= 0) {
	    return new byte[0];
	}
	if (bytesRead == buffer.length) {
	    return buffer;
	}
	byte[] readBytes = new byte[bytesRead];
	for (int i = 0; i < bytesRead; i++) {
	    readBytes[i] = buffer[i];
	}
	return readBytes;
    }

    private byte[] concatenateBytes(List<byte[]> toConcatBytesArrays) throws IOException {
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	for (byte[] toConcatBytes : toConcatBytesArrays) {
	    outputStream.write(toConcatBytes);
	}
	return outputStream.toByteArray();
    }

    private String readMethod(String firstLine) {
	int indexOfSeparator = firstLine.indexOf("/");
	if (indexOfSeparator <= MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	return firstLine.substring(0, indexOfSeparator - 1);
    }

    private String readPath(String firstLine) {
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

    private Header readHeader(String headerToParse) {
	String[] keyValue = headerToParse.split(HEADER_KEY_VALUE_SEPARATOR);
	if (keyValue.length < 2) {
	    throw new RequestException(keyValue[0] + " is not a proper header");
	}
	return new HttpHeader(keyValue[0].trim(), keyValue[1].trim());
    }

    @Override
    public void write(OutputStream outputStream, Response response) throws IOException{
	StringBuilder builder = new StringBuilder();
	Header currentDateHeader = new DateHeader(LocalDateTime.now(ZoneOffset.UTC));
	builder.append(responseCodeToString(response.responseCode())).append(NEW_LINE_SEPARATOR)
		.append(currentDateHeader);
	for (Header header : additionalResponseHeaders) {
	    builder.append(NEW_LINE_SEPARATOR).append(header);
	}
	String contentType = "";
	for (Header header : response.headers()) {
	    if (header.is(CONTENT_TYPE_HEADER_KEY)) {
		contentType = header.value();
	    }
	    builder.append(NEW_LINE_SEPARATOR).append(header);
	}
	if (!response.hadBody()) {
	    outputStream.write(builder.toString().getBytes());
	    return;
	}
	builder.append(NEW_LINE_SEPARATOR)
		.append(HEADERS_BODY_SEPARATOR);
	if (contentType.equals(JSON_CONTENT_TYPE) || contentType.equals(TEXT_PLAIN_CONTENT_TYPE)) {
	    builder.append(new String(response.body()));
	} else {
	    builder.append(response.body());
	}
	outputStream.write(builder.toString().getBytes());
    }

    private String responseCodeToString(int responseCode) {
	return RESPONSE_CODE_HTTP_1_1_PREFIX + responseCode;
    }

}
