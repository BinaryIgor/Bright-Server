package com.iprogrammerr.bright.server.configuration;

import java.util.Properties;

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

    public String getContextPath() {
	return contextPath;
    }

    public int getPort() {
	return port;
    }

    public int getTimeOutMillis() {
	return timeOutMillis;
    }

    public boolean isAddCorsHeaders() {
	return addCorsHeaders;
    }

    public String getAllowedOrigin() {
	return allowedOrigin;
    }

    public String getAllowedMethods() {
	return allowedMethods;
    }

    public String getAllowedHeaders() {
	return allowedHeaders;
    }

}
