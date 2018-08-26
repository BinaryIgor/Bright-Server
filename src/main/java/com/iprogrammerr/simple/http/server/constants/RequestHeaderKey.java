package com.iprogrammerr.simple.http.server.constants;

public enum RequestHeaderKey {
    ACCEPT("Accept"), ACCEPT_CHARSET("Accept-Charset"), ACCESS_CONTROL_REQUEST_METHOD(
	    "Access-Control-Request-Method"), ACCESS_CONTROL_REQUEST_HEADERS(
		    "Access-Control-Request-Headers"), AUTHORIZATION("Authorization"), CACHE_CONTROL(
			    "Cache-Control"), CONTENT_LENGTH("Content-Length"), CONTENT_TYPE("Content-Type"), DATE(
				    "Date"), EXPECT("Expect"), FORWARDED("Forwarded"), FROM(
					    "From"), HOST("Host"), IF_MATCH("If-Match"), IF_MODIFIED_SINCE(
						    "If-Modified-Since"), IF_NONE_MATCH("If-None-Match"), ORIGIN(
							    "Origin"), PROXY_AUTHORIZATION(
								    "Proxy-Authorization"), REFERER(
									    "Referer"), USER_AGET("User-Agent");

    private String value;

    RequestHeaderKey(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }

}
