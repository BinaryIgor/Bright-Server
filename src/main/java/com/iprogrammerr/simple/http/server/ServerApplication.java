package com.iprogrammerr.simple.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.iprogrammerr.simple.http.server.configuration.ServerConfiguration;
import com.iprogrammerr.simple.http.server.example.SimpleController;
import com.iprogrammerr.simple.http.server.filter.RequestFilter;
import com.iprogrammerr.simple.http.server.resolver.RequestResolver;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
	ServerConfiguration serverConfiguration = new ServerConfiguration(getServerProperties());

	SimpleController simpleController = new SimpleController();

	List<RequestResolver> requestResolvers = new ArrayList<>();
	requestResolvers.addAll(simpleController.getRequestResolvers());

	List<RequestFilter> requestFilters = new ArrayList<>();
	// requestFilters.add(new AuthorizationFilter());

	Server server = new Server(serverConfiguration, requestResolvers, requestFilters);
	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	    server.stop();
	}));
	server.start();
    }

    private static Properties getServerProperties() throws IOException {
	InputStream inputStream = ServerApplication.class.getResourceAsStream("/server.properties");
	Properties properties = new Properties();
	properties.load(inputStream);
	inputStream.close();
	return properties;
    }
}
