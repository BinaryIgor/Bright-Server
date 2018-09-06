package com.iprogrammerr.bright.server.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.iprogrammerr.bright.server.header.AccessControlAllowHeadersHeader;
import com.iprogrammerr.bright.server.header.AccessControlAllowMethodsHeader;
import com.iprogrammerr.bright.server.header.AccessControlAllowOriginHeader;
import com.iprogrammerr.bright.server.header.Header;

//TODO add more logic here
public class ServerConfiguration {

    private String contextPath;
    private int port;
    private int timeOutMillis;
    private boolean addCorsHeaders;
    private String allowedOrigin;
    private String allowedMethods;
    private String allowedHeaders;

    public ServerConfiguration(Properties properties) {
	contextPath = properties.getProperty("contextPath", "");
	port = Integer.parseInt(properties.getProperty("port", "8080"));
	timeOutMillis = Integer.parseInt(properties.getProperty("timeOutMillis", "5000"));
	addCorsHeaders = Boolean.parseBoolean(properties.getProperty("addCorsHeaders", "false"));
	allowedOrigin = properties.getProperty("allowedOrigins", "*");
	allowedMethods = properties.getProperty("allowedMethods", "*");
	allowedHeaders = properties.getProperty("allowedHeaders", "*");
    }

    public String contextPath() {
	return contextPath;
    }

    public int port() {
	return port;
    }

    public int timeOutMillis() {
	return timeOutMillis;
    }

    public boolean addCorsHeaders() {
	return addCorsHeaders;
    }

    public List<Header> corsHeaders() {
	List<Header> corsHeaders = new ArrayList<>();
	corsHeaders.add(new AccessControlAllowHeadersHeader(allowedHeaders));
	corsHeaders.add(new AccessControlAllowMethodsHeader(allowedMethods));
	corsHeaders.add(new AccessControlAllowOriginHeader(allowedOrigin));
	return corsHeaders;
    }

    public String allowedOrigin() {
	return allowedOrigin;
    }

    public String allowedMethods() {
	return allowedMethods;
    }

    public String allowedHeaders() {
	return allowedHeaders;
    }

}
