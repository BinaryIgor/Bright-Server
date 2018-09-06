package com.iprogrammerr.bright.server.request;

import com.iprogrammerr.bright.server.configuration.ServerConfiguration;

public class ConfigurableCors implements Cors {

    private static final String ALLOW_ALL = "*";
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private String accessControllAllowOrigin;
    private String accessControllAllowHeaders;
    private String accessControllAllowMethods;

    public ConfigurableCors(String accessControllAllowOrigin, String accessControllAllowHeaders,
	    String accessControllAllowMethods) {
	this.accessControllAllowOrigin = accessControllAllowOrigin;
	this.accessControllAllowHeaders = accessControllAllowHeaders;
	this.accessControllAllowMethods = accessControllAllowMethods;
    }

    public ConfigurableCors(ServerConfiguration serverConfiguration) {
	this(serverConfiguration.allowedOrigin(), serverConfiguration.allowedHeaders(),
		serverConfiguration.allowedMethods());
    }

    @Override
    public boolean validate(Request request) {
	boolean haveRequiredHeaders = request.hasHeader(ACCESS_CONTROL_ALLOW_ORIGIN)
		&& request.hasHeader(ACCESS_CONTROL_ALLOW_HEADERS) && request.hasHeader(ACCESS_CONTROL_ALLOW_METHODS);
	if (!haveRequiredHeaders) {
	    return false;
	}
	boolean originAllowed = accessControllAllowOrigin.equals(ALLOW_ALL)
		|| accessControllAllowOrigin.equals(request.header(ACCESS_CONTROL_ALLOW_ORIGIN));
	if (!originAllowed) {
	    return false;
	}
	boolean methodAllowed = accessControllAllowMethods.equals(ALLOW_ALL)
		|| accessControllAllowMethods.contains(request.header(ACCESS_CONTROL_ALLOW_METHODS));
	if (!methodAllowed) {
	    return false;
	}
	boolean headersAllowed = accessControllAllowHeaders.equals(ALLOW_ALL) || 
		accessControllAllowHeaders.contains(request.header(ACCESS_CONTROL_ALLOW_HEADERS));
	if (!headersAllowed) {
	    return false;
	}
	return true;
    }

}
