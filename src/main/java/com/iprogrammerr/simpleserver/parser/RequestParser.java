package com.iprogrammerr.simpleserver.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simpleserver.exception.RequestException;
import com.iprogrammerr.simpleserver.model.Header;
import com.iprogrammerr.simpleserver.model.Request;

public class RequestParser {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String HEADERS_BODY_PARSED_SEPARATOR = "\r";

    public Request getRequest(InputStream inputStream) throws IOException {
	String[] requestLines = readRequest(inputStream);
	if (requestLines.length < 1) {
	    throw new RequestException();
	}
	String[] splitedFirstLine = requestLines[0].split("/");
	if (splitedFirstLine.length < 1) {
	    throw new RequestException();
	}
	String method = getMethod(splitedFirstLine[0].trim());
	String path = splitedFirstLine[1].replaceAll(" HTTP", "");
	List<Header> headers = new ArrayList<>();
	int i;
	for (i = 1; i < requestLines.length; i++) {
	    String line = requestLines[i];
	    if (line.isEmpty() || line.equals(HEADERS_BODY_PARSED_SEPARATOR)) {
		break;
	    }
	    headers.add(getHeader(line));
	}
	String body = "";
	if ((i + 1) < requestLines.length && requestLines[i].equals(HEADERS_BODY_PARSED_SEPARATOR)) {
	    body = requestLines[i + 1];
	}
	return new Request(method, path, headers, body);
    }

    private String[] readRequest(InputStream inputStream) throws IOException {
	BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
	int bytesToRead = bufferedInputStream.available();
	byte[] bytes = new byte[bytesToRead];
	bufferedInputStream.read(bytes, 0, bytes.length);
	String request = new String(bytes);
	if (request.isEmpty()) {
	    throw new RequestException();
	}
	return request.split(NEW_LINE_SEPARATOR);
    }

    private String getMethod(String methodToParse) {
	String[] parsed = methodToParse.split("/");
	if (parsed.length < 1) {
	    throw new RequestException();
	}
	return parsed[0].trim();
    }

    private Header getHeader(String headerToParse) {
	String[] keyValue = headerToParse.split(":");
	if (keyValue.length < 2) {
	    throw new RequestException();
	}
	return new Header(keyValue[0], keyValue[1]);
    }

}
