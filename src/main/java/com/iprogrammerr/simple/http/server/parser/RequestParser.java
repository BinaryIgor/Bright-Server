package com.iprogrammerr.simple.http.server.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.simple.http.server.constants.ParserConstants;
import com.iprogrammerr.simple.http.server.exception.RequestException;
import com.iprogrammerr.simple.http.server.model.Header;
import com.iprogrammerr.simple.http.server.model.Parameters;
import com.iprogrammerr.simple.http.server.model.Request;

public class RequestParser {

    private UrlParser urlParser;

    public RequestParser(UrlParser urlParser) {
	this.urlParser = urlParser;
    }

    public Request getRequest(InputStream inputStream) throws IOException {
	List<String> requestLines = readRequest(inputStream);
	if (requestLines.size() < 1 || requestLines.get(0).length() < ParserConstants.MIN_VALID_FIRST_LINE_LENGTH) {
	    throw new RequestException();
	}
	String method = getMethod(requestLines.get(0));
	System.out.println("Method = " + method);
	String path = getPath(requestLines.get(0));
	System.out.println("path = " + path);
	Parameters parameters = urlParser.getParameters(path);
	List<Header> headers = new ArrayList<>();
	int i;
	for (i = 1; i < requestLines.size(); i++) {
	    String line = requestLines.get(i);
	    if (line.isEmpty() || line.equals(ParserConstants.HEADERS_BODY_PARSED_SEPARATOR)) {
		break;
	    }
	    headers.add(getHeader(line));
	}
	byte[] body = new byte[0];
	if ((i + 1) < requestLines.size()
		&& requestLines.get(i).equals(ParserConstants.HEADERS_BODY_PARSED_SEPARATOR)) {
	    body = requestLines.get(i + 1).getBytes();
	}
	return new Request(method, path, headers, parameters, body);
    }

    private List<String> readRequest(InputStream inputStream) throws IOException {
	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	List<String> lines = new ArrayList<>();
	String line = bufferedReader.readLine();
	while (line != null && !line.isEmpty()) {
	    System.out.println(line);
	    lines.add(line);
	    line = bufferedReader.readLine();
	}
	return lines;
    }

    private String getMethod(String firstLine) {
	int indexOfSeparator = firstLine.indexOf("/");
	if (indexOfSeparator <= ParserConstants.MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	return firstLine.substring(0, indexOfSeparator - 1);
    }

    private String getPath(String firstLine) {
	int indexOfSeparator = firstLine.indexOf("/");
	if (indexOfSeparator <= ParserConstants.MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	int indexOfHttp = firstLine.indexOf("HTTP");
	if (indexOfHttp <= ParserConstants.MIN_REQUEST_METHOD_LENGTH) {
	    throw new RequestException();
	}
	return firstLine.substring(indexOfSeparator + 1, indexOfHttp).trim();
    }

    private Header getHeader(String headerToParse) {
	String[] keyValue = headerToParse.split(":");
	if (keyValue.length < 2) {
	    throw new RequestException();
	}
	return new Header(keyValue[0], keyValue[1]);
    }

}
