package com.iprogrammerr.simpleserver.configuration;

import java.util.Properties;

public class ServerConfiguration {

    private String contextPath;
    private int port;
    private String allowedOrigins;

    public ServerConfiguration(Properties properties) {
	contextPath = properties.getProperty("contextPath", "");
	port = Integer.parseInt(properties.getProperty("port", "8080"));
	allowedOrigins = properties.getProperty("allowedOrigins", "*");
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

}
