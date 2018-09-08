package com.iprogrammerr.bright.server.configuration;

import java.util.Properties;

import com.iprogrammerr.bright.server.loading.Loading;
import com.iprogrammerr.bright.server.loading.StickyLoading;

public class BrightServerConfiguration implements ServerConfiguration {

    private String contextPath;
    private int port;
    private int timeout;
    private boolean addCorsHeaders;
    private String allowedOrigin;
    private String allowedMethods;
    private String allowedHeaders;
    private Loading<Cors> cors;

    public BrightServerConfiguration(Properties properties) {
	contextPath = properties.getProperty("contextPath", "");
	port = Integer.parseInt(properties.getProperty("port", "8080"));
	timeout = Integer.parseInt(properties.getProperty("timeout", "5000"));
	addCorsHeaders = Boolean.parseBoolean(properties.getProperty("addCorsHeaders", "false"));
	allowedOrigin = properties.getProperty("allowedOrigins", "*");
	allowedMethods = properties.getProperty("allowedMethods", "*");
	allowedHeaders = properties.getProperty("allowedHeaders", "*");
	cors = new StickyLoading<>(() -> {
	    if (addCorsHeaders) {
		return new ConfigurableCors(allowedOrigin, allowedHeaders, allowedMethods);
	    }
	    return new DefaultCors();
	});
    }

    @Override
    public String contextPath() {
	return contextPath;
    }

    @Override
    public int port() {
	return port;
    }

    @Override
    public int timeout() {
	return timeout;
    }

    public boolean addCorsHeaders() {
	return addCorsHeaders;
    }

    @Override
    public Cors cors() {
	return cors.load();
    }

}
