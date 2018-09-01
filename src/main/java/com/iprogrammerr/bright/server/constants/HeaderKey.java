package com.iprogrammerr.bright.server.constants;

public enum HeaderKey {
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"), ACCESS_CONTROL_ALLOW_CREDENTIALS( "Access-Control-Allow-Credentials"), 
    ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"), ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
    ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"), ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"), 
    ACCEPT_PATCH("Accept-Patch"), ACCEPT_RANGES("Accept-Ranges"), AGE("Age"), ALLOW("Allow"), CACHE_CONTROL("Cache-Control"),
    CONTENT_DISPOSITION("Content-Disposition"), CONTENT_ENCODING("Content-Encoding"), CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LENGTH("Content-Length"), CONTENT_TYPE("Content-Type"), DATE("Date"), EXPIRES( "Expires"), LAST_MODIFIED("Last-Modified"), PROXY_AUTHENTICATE("Proxy-Authenticate"), 
    SERVER("Server"), WWW_AUTHENTICATE( "WWW-Authenticate"), ACCEPT("Accept"), ACCEPT_CHARSET("Accept-Charset"), ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"), 
    ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"), AUTHORIZATION("Authorization"), EXPECT("Expect"), FORWARDED("Forwarded"), FROM("From"), HOST("Host"), IF_MATCH("If-Match"), IF_MODIFIED_SINCE("If-Modified-Since"), IF_NONE_MATCH("If-None-Match"), 
    ORIGIN("Origin"), PROXY_AUTHORIZATION( "Proxy-Authorization"), REFERER( "Referer"), USER_AGENT( "User-Agent");

    private String value;

    HeaderKey(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }

}
