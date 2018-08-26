package com.iprogrammerr.simple.http.server.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.iprogrammerr.simple.http.server.constants.HeadersValues;
import com.iprogrammerr.simple.http.server.constants.RequestHeaderKey;
import com.iprogrammerr.simple.http.server.constants.ResponseHeaderKey;

public class Header {

    private String key;
    private String value;

    public Header(String key, String value) {
	this.key = key;
	this.value = value;
    }

    public String getKey() {
	return key;
    }

    public String getValue() {
	return value;
    }

    public static Header createJsonContentType() {
	return new Header(RequestHeaderKey.CONTENT_TYPE.getValue(), HeadersValues.JSON_CONTENT_TYPE);
    }

    public static Header createTextContentType() {
	return new Header(RequestHeaderKey.CONTENT_TYPE.getValue(), HeadersValues.TEXT_PLAIN_CONTENT_TYPE);
    }

    public static Header createContentLengthHeader(int contentLength) {
	return new Header(RequestHeaderKey.CONTENT_LENGTH.getValue(), String.valueOf(contentLength));
    }

    public static Header createAccessControlAllowOriginsHeader(String allowedOrigins) {
	return new Header(ResponseHeaderKey.ACCESS_CONTROL_ALLOW_ORIGIN.getValue(), allowedOrigins);
    }

    public static Header createAccessControlAllowHeadersHeader(String allowedHeaders) {
	return new Header(ResponseHeaderKey.ACCESS_CONTROL_ALLOW_HEADERS.getValue(), allowedHeaders);
    }

    public static Header createAccessControlAllowMethodsHeader(String allowedMethods) {
	return new Header(ResponseHeaderKey.ACCESS_CONTROL_ALLOW_METHODS.getValue(), allowedMethods);
    }

    public static Header createCurrentDateHeader() {
	LocalDateTime currentDate = LocalDateTime.now(ZoneOffset.UTC);
	System.out.println(currentDate);
	return new Header(ResponseHeaderKey.DATE.getValue(), currentDate.toString());
    }

    @Override
    public String toString() {
	return key + ": " + value;
    }

}
