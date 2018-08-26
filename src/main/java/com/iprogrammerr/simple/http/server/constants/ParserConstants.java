package com.iprogrammerr.simple.http.server.constants;

public class ParserConstants {

    public static final int MIN_VALID_FIRST_LINE_LENGTH = 10;
    public static final int MIN_REQUEST_METHOD_LENGTH = 3;
    public static final String HEADERS_BODY_SEPARATOR = "\r\n\r\n";
    public static final String NEW_LINE_SEPARATOR = "\n";
    public static final String HEADERS_BODY_PARSED_SEPARATOR = "\r";
    public static final String URL_SEGMENTS_SEPARATOR = "/";
    public static final String PARAMETERS_BEGINING = "?";
    public static final String PARAMETERS_SEPARATOR = "&";
    public static final String PARAMETERS_KEY_VALUE_SEPARATOR = "=";
    public static final String URL_PATTERN_PATH_VARIABLE_START = "{";
    public static final String URL_PATTERN_PATH_VARIABLE_END = "}";
    public static final String URL_PATTERN_PATH_VARIABLE_KEY_TYPE_SEPARATOR = ":";
    public static final String HTTP = "HTTP";
    public static final String RESPONSE_CODE_HTTP_1_1_PREFIX = "HTTP/1.1 ";
}
