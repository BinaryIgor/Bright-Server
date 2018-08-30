package com.iprogrammerr.bright.server.constants;

public enum ResponseHeaderKey {
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"), ACCESS_CONTROL_ALLOW_CREDENTIALS(
	    "Access-Control-Allow-Credentials"), ACCESS_CONTROL_EXPOSE_HEADERS(
		    "Access-Control-Expose-Headers"), ACCESS_CONTROL_MAX_AGE(
			    "Access-Control-Max-Age"), ACCESS_CONTROL_ALLOW_METHODS(
				    "Access-Control-Allow-Methods"), ACCESS_CONTROL_ALLOW_HEADERS(
					    "Access-Control-Allow-Headers"), ACCEPT_PATCH(
						    "Accept-Patch"), ACCEPT_RANGES("Accept-Ranges"), AGE("Age"), ALLOW(
							    "Allow"), CACHE_CONTROL(
								    "Cache-Control"), CONTENT_DISPOSITION(
									    "Content-Disposition"), CONTENT_ENCODING(
										    "Content-Encoding"), CONTENT_LANGUAGE(
											    "Content-Language"), CONTENT_LENGTH(
												    "Content-Length"), CONTENT_TYPE(
													    "Content-Type"), DATE(
														    "Date"), EXPIRES(
															    "Expires"), LAST_MODIFIED(
																    "Last-Modified"), PROXY_AUTHENTICATE(
																	    "Proxy-Authenticate"), SERVER(
																		    "Server"), WWW_AUTHENTICATE(
																			    "WWW-Authenticate");

    private String value;

    ResponseHeaderKey(String value) {
	this.value = value;
    }

    public String getValue() {
	return value;
    }

}
