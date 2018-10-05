package com.iprogrammerr.bright.server.cors;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowHeadersHeader;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowMethodsHeader;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowOriginHeader;
import com.iprogrammerr.bright.server.request.Request;

public final class ConfigurableCors implements Cors {

    private static final String ALLOW_ALL = "*";
    private static final String ACCESS_CONTROL_ORIGIN = "Origin";
    private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
    private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    private final String allowOrigin;
    private final String allowHeaders;
    private final String allowMethods;
    private final List<Header> headers;

    public ConfigurableCors(String allowOrigin, String allowHeaders, String allowMethods) {
	this.allowOrigin = allowOrigin;
	this.allowHeaders = allowHeaders;
	this.allowMethods = allowMethods;
	this.headers = new ArrayList<>();
    }

    @Override
    public boolean isValid(Request request) {
	boolean valid = request.hasHeader(ACCESS_CONTROL_ORIGIN) && request.hasHeader(ACCESS_CONTROL_REQUEST_HEADERS)
		&& request.hasHeader(ACCESS_CONTROL_REQUEST_METHOD);
	if (valid) {
	    try {
		valid = (this.allowOrigin.equals(ALLOW_ALL)
			|| this.allowOrigin.equals(request.header(ACCESS_CONTROL_ORIGIN)))
			&& (this.allowMethods.equals(ALLOW_ALL)
				|| this.allowMethods.contains(request.header(ACCESS_CONTROL_REQUEST_METHOD)))
			&& (this.allowHeaders.equals(ALLOW_ALL)
				|| this.allowHeaders.contains(request.header(ACCESS_CONTROL_REQUEST_HEADERS)));
	    } catch (Exception e) {
		valid = false;
	    }
	}
	return valid;
    }

    @Override
    public List<Header> toAddHeaders() {
	if (this.headers.isEmpty()) {
	    this.headers.add(new AccessControlAllowHeadersHeader(this.allowHeaders));
	    this.headers.add(new AccessControlAllowMethodsHeader(this.allowMethods));
	    this.headers.add(new AccessControlAllowOriginHeader(this.allowOrigin));
	}
	return headers;
    }

}
