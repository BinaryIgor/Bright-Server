package com.iprogrammerr.bright.server.configuration;

import java.util.Properties;

import com.iprogrammerr.bright.server.loading.Loading;
import com.iprogrammerr.bright.server.loading.StickyLoading;

public class BrightServerConfiguration implements ServerConfiguration {

    private final Loading<Cors> cors;
    private final Properties source;

    public BrightServerConfiguration(Properties source) {
	this.source = source;
	cors = new StickyLoading<>(() -> {
	    if (addCorsHeaders()) {
		return new ConfigurableCors(source.getProperty("allowedOrigins", "*"),
			source.getProperty("allowedMethods", "*"), source.getProperty("allowedHeaders", "*"));
	    }
	    return new DefaultCors();
	});
    }

    @Override
    public String contextPath() {
	return source.getProperty("contextPath", "");
    }

    @Override
    public int port() {
	try {
	    return Integer.parseInt(source.getProperty("port", "8080"));
	} catch (Exception exception) {
	    return 8080;
	}
    }

    @Override
    public int timeout() {
	try {
	    return Integer.parseInt(source.getProperty("timeout", "5000"));
	} catch (Exception exception) {
	    return 5000;
	}
    }

    public boolean addCorsHeaders() {
	return Boolean.parseBoolean(source.getProperty("addCorsHeaders", "false"));
    }

    @Override
    public Cors cors() {
	return cors.load();
    }

}
