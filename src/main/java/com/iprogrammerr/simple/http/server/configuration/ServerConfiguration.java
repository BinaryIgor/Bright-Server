package com.iprogrammerr.simple.http.server.configuration;

import java.util.Properties;

public class ServerConfiguration {

    private String contextPath;
    private int port;
    private String allowedOrigins;
    private String allowedMethods;
    private String allowedHeaders;

    public ServerConfiguration(Properties properties) {
	contextPath = properties.getProperty("contextPath", "");
	port = Integer.parseInt(properties.getProperty("port", "8080"));
	allowedOrigins = properties.getProperty("allowedOrigins", "*");
	allowedMethods = properties.getProperty("allowedMethods", "*");
	allowedHeaders = properties.getProperty("allowedHeaders", "*");
    }

    public String getContextPath() {
	return contextPath;
    }

    public int getPort() {
	return port;
    }

    public String getAllowedOrigins() {
	return allowedOrigins;
    }

    public String getAllowedMethods() {
	return allowedMethods;
    }

    public String getAllowedHeaders() {
	return allowedHeaders;
    }

}
