package com.iprogrammerr.bright.server.test;

import java.util.Arrays;

import com.iprogrammerr.bright.server.header.template.AccessControlAllowHeadersHeader;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowMethodsHeader;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowOriginHeader;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.request.RequestEnvelope;

public final class PreflightCorsRequest extends RequestEnvelope {

	public PreflightCorsRequest(String url, String origin, String requestHeaders, String requestMethods, byte[] body) {
		super(new ParsedRequest(url, "OPTIONS",
				Arrays.asList(new AccessControlAllowOriginHeader(origin),
						new AccessControlAllowHeadersHeader(requestHeaders),
						new AccessControlAllowMethodsHeader(requestMethods)),
				body));
	}

	public PreflightCorsRequest(String url, String origin, String requestHeaders, String requestMethods) {
		this(url, origin, requestHeaders, requestMethods, new byte[0]);
	}

	public PreflightCorsRequest(String url, byte[] body) {
		this(url, "*", "*", "*", body);
	}

	public PreflightCorsRequest(String url) {
		this(url, new byte[0]);
	}
}
