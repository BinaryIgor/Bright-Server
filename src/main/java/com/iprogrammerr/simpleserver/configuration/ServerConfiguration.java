package com.iprogrammerr.simpleserver.configuration;

import java.util.Properties;

public class ServerConfiguration {

    private String contextPath;

    public ServerConfiguration(Properties properties) {
	contextPath = properties.getProperty("contextPath");
    }

    public String getContextPath() {
	return contextPath;
    }

}
