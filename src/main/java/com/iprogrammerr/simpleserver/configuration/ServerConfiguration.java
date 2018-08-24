package com.iprogrammerr.simpleserver.configuration;

import java.util.Properties;

public class ServerConfiguration {

    private String contextPath;
    private int port;

    public ServerConfiguration(Properties properties) {
	contextPath = properties.getProperty("contextPath", "");
	port = Integer.parseInt(properties.getProperty("port", "8080"));
    }

    public String getContextPath() {
	return contextPath;
    }

    public int getPort() {
	return port;
    }

}
