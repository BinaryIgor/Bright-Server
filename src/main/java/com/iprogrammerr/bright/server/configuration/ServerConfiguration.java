package com.iprogrammerr.bright.server.configuration;

import java.util.Properties;

public class ServerConfiguration {

    private String contextPath;
    private int port;
    private boolean addCorsHeaders;
    private String allowedOrigin;
    private String allowedMethods;
    private String allowedHeaders;

    public ServerConfiguration(Properties properties) {
	contextPath = properties.getProperty("contextPath", "");
	addCorsHeaders = Boolean.parseBoolean(properties.getProperty("addCorsHeaders", "false"));
	port = Integer.parseInt(properties.getProperty("port", "8080"));
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
