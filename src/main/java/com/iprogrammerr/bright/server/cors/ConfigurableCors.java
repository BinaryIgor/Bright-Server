package com.iprogrammerr.bright.server.cors;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowHeadersHeader;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowMethodsHeader;
import com.iprogrammerr.bright.server.header.template.AccessControlAllowOriginHeader;
import com.iprogrammerr.bright.server.initialization.Initialization;
import com.iprogrammerr.bright.server.initialization.StickyInitialization;
import com.iprogrammerr.bright.server.method.OptionsMethod;
import com.iprogrammerr.bright.server.request.Request;

public final class ConfigurableCors implements Cors {

	private static final OptionsMethod OPTIONS = new OptionsMethod();
	private static final String ALLOW_ALL = "*";
	private static final String ACCESS_CONTROL_ORIGIN = "Origin";
	private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
	private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
	private final String allowOrigin;
	private final String allowHeaders;
	private final String allowMethods;
	private final Initialization<List<Header>> headers;

	private ConfigurableCors(String allowOrigin, String allowHeaders, String allowMethods,
			Initialization<List<Header>> headers) {
		this.allowOrigin = allowOrigin;
		this.allowHeaders = allowHeaders;
		this.allowMethods = allowMethods;
		this.headers = headers;
	}

	public ConfigurableCors(String allowOrigin, String allowHeaders, String allowMethods) {
		this(allowOrigin, allowHeaders, allowMethods, new StickyInitialization<>(() -> {
			List<Header> headers = new ArrayList<>();
			headers.add(new AccessControlAllowHeadersHeader(allowHeaders));
			headers.add(new AccessControlAllowMethodsHeader(allowMethods));
			headers.add(new AccessControlAllowOriginHeader(allowOrigin));
			return headers;
		}));
	}

	@Override
	public boolean isValid(Request request) {
		boolean valid = is(request);
		if (valid) {
			try {
				valid = (this.allowOrigin.equals(ALLOW_ALL)
						|| this.allowOrigin.equals(request.header(ACCESS_CONTROL_ORIGIN)))
						&& (this.allowMethods.equals(ALLOW_ALL) || this.allowMethods
								.contains(request.header(ACCESS_CONTROL_REQUEST_METHOD)))
						&& (this.allowHeaders.equals(ALLOW_ALL) || this.allowHeaders
								.contains(request.header(ACCESS_CONTROL_REQUEST_HEADERS)));
			} catch (Exception e) {
				valid = false;
			}
		}
		return valid;
	}

	@Override
	public List<Header> toAddHeaders() {
		return this.headers.value();
	}

	@Override
	public boolean is(Request request) {
		return OPTIONS.is(request.method()) && request.hasHeader(ACCESS_CONTROL_ORIGIN)
				&& request.hasHeader(ACCESS_CONTROL_REQUEST_HEADERS)
				&& request.hasHeader(ACCESS_CONTROL_REQUEST_METHOD);
	}
}
