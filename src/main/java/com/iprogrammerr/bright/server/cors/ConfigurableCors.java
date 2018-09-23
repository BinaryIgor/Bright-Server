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
    private final String accessControllAllowOrigin;
    private final String accessControllAllowHeaders;
    private final String accessControllAllowMethods;
    private final List<Header> corsHeaders;

    public ConfigurableCors(String accessControllAllowOrigin, String accessControllAllowHeaders,
	    String accessControllAllowMethods) {
	this.accessControllAllowOrigin = accessControllAllowOrigin;
	this.accessControllAllowHeaders = accessControllAllowHeaders;
	this.accessControllAllowMethods = accessControllAllowMethods;
	this.corsHeaders = new ArrayList<>();
    }

    @Override
    public boolean validate(Request request) {
	boolean haveRequiredHeaders = request.hasHeader(ACCESS_CONTROL_ORIGIN)
		&& request.hasHeader(ACCESS_CONTROL_REQUEST_HEADERS)
		&& request.hasHeader(ACCESS_CONTROL_REQUEST_METHOD);
	if (!haveRequiredHeaders) {
	    return false;
	}
	try {
	    boolean originAllowed = accessControllAllowOrigin.equals(ALLOW_ALL)
		    || accessControllAllowOrigin.equals(request.header(ACCESS_CONTROL_ORIGIN));
	    if (!originAllowed) {
		return false;
	    }
	    boolean methodAllowed = accessControllAllowMethods.equals(ALLOW_ALL)
		    || accessControllAllowMethods.contains(request.header(ACCESS_CONTROL_REQUEST_METHOD));
	    if (!methodAllowed) {
		return false;
	    }
	    boolean headersAllowed = accessControllAllowHeaders.equals(ALLOW_ALL)
		    || accessControllAllowHeaders.contains(request.header(ACCESS_CONTROL_REQUEST_HEADERS));
	    if (!headersAllowed) {
		return false;
	    }
	} catch (Exception exception) {
	    return false;
	}
	return true;
    }

    @Override
    public List<Header> toAddHeaders() {
	if (corsHeaders.isEmpty()) {
	    corsHeaders.add(new AccessControlAllowHeadersHeader(accessControllAllowHeaders));
	    corsHeaders.add(new AccessControlAllowMethodsHeader(accessControllAllowMethods));
	    corsHeaders.add(new AccessControlAllowOriginHeader(accessControllAllowOrigin));
	}
	return corsHeaders;
    }

}
