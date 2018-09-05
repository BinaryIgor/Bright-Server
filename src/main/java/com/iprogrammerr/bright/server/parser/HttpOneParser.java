package com.iprogrammerr.bright.server.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.configuration.ServerConfiguration;
import com.iprogrammerr.bright.server.constants.HeaderKey;
import com.iprogrammerr.bright.server.exception.RequestException;
import com.iprogrammerr.bright.server.model.Header;
import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.response.Response;

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
    private Header accessControlAllowedHeadersHeader;
    private Header accessControllAllowedMethodsHeader;
    private Header accessControllAllowedOriginsHeader;
    private List<Header> additionalResponseHeaders;

    public HttpOneParser(ServerConfiguration serverConfiguration, List<Header> additionalHeaders) {
	accessControlAllowedHeadersHeader = new Header(HeaderKey.ACCESS_CONTROL_ALLOW_HEADERS,
		serverConfiguration.getAllowedHeaders());
	accessControllAllowedMethodsHeader = new Header(HeaderKey.ACCESS_CONTROL_ALLOW_METHODS,
		serverConfiguration.getAllowedMethods());
	accessControllAllowedOriginsHeader = new Header(HeaderKey.ACCESS_CONTROL_ALLOW_ORIGIN,
		serverConfiguration.getAllowedOrigin());
	this.additionalResponseHeaders = additionalHeaders;
    }

    public HttpOneParser(ServerConfiguration serverConfiguration) {
	this(serverConfiguration, new ArrayList<>());
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
	    if (header.isContentLength()) {
		bodyBytes = bodyLength(header);
	    }
	    headers.add(header);
	}
	if (bodyBytes == 0) {
	    return new Request(method, path, headers);
	}
	byte[] partOfTheBody;
	if ((i + 1) < requestLines.length) {
	    partOfTheBody = requestLines[i + 1].getBytes();
	} else {
	    partOfTheBody = new byte[0];
	}
	if (partOfTheBody.length == bodyBytes) {
	    return new Request(method, path, headers, partOfTheBody);
	}
	byte[] body = readBody(inputStream, partOfTheBody, bodyBytes);
	return new Request(method, path, headers, body);
    }
    
    private int bodyLength(Header header) {
	try {
	    int bodyLength = Integer.parseInt(header.getValue());
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
	    return new Header("super", "super");
	}
	return new Header(keyValue[0].trim(), keyValue[1].trim());
    }

    @Override
    public byte[] write(Response response) {
	StringBuilder builder = new StringBuilder();
	LocalDateTime currentDate = LocalDateTime.now(ZoneOffset.UTC);
	Header currentDateHeader = new Header(HeaderKey.DATE, currentDate.toString());
	builder.append(responseCodeToString(response.getResponseCode())).append(NEW_LINE_SEPARATOR)
		.append(accessControlAllowedHeadersHeader).append(NEW_LINE_SEPARATOR)
		.append(accessControllAllowedMethodsHeader).append(NEW_LINE_SEPARATOR)
		.append(accessControllAllowedOriginsHeader).append(NEW_LINE_SEPARATOR).append(currentDateHeader);
	for (Header header : additionalResponseHeaders) {
	    builder.append(NEW_LINE_SEPARATOR).append(header);
	}
	String contentType = "";
	for (Header header : response.getHeaders()) {
	    if (header.getKey().equals(HeaderKey.CONTENT_TYPE.getValue())) {
		contentType = header.getValue();
	    }
	    builder.append(NEW_LINE_SEPARATOR).append(header);
	}
	if (!response.hasBody()) {
	    return builder.toString().getBytes();
	}
	builder.append(NEW_LINE_SEPARATOR)
		.append(new Header(HeaderKey.CONTENT_LENGTH, String.valueOf(response.getBody().length)))
		.append(HEADERS_BODY_SEPARATOR);
	if (contentType.equals(JSON_CONTENT_TYPE) || contentType.equals(TEXT_PLAIN_CONTENT_TYPE)) {
	    builder.append(new String(response.getBody()));
	} else {
	    builder.append(response.getBody());
	}
	return builder.toString().getBytes();
    }

    private String responseCodeToString(int responseCode) {
	return RESPONSE_CODE_HTTP_1_1_PREFIX + responseCode;
    }

}
