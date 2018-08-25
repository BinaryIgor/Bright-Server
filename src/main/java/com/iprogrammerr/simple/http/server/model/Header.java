package com.iprogrammerr.simple.http.server.model;

import com.iprogrammerr.simple.http.server.constants.HeadersValues;
import com.iprogrammerr.simple.http.server.constants.RequestHeaderKey;

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

    public String getWritable() {
	return key + ": " + value;
    }

    @Override
    public String toString() {
	return "Header [key=" + key + ", value=" + value + "]";
    }

}
