package com.iprogrammerr.simpleserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.iprogrammerr.simpleserver.configuration.ServerConfiguration;
import com.iprogrammerr.simpleserver.controller.SimpleController;
import com.iprogrammerr.simpleserver.resolver.RequestResolver;
import com.iprogrammerr.simpleserver.resolver.RequestsResolver;
import com.iprogrammerr.simpleserver.server.Server;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
	ServerConfiguration serverConfiguration = new ServerConfiguration(getServerProperties());

	SimpleController simpleController = new SimpleController();

	List<RequestResolver> requestResolvers = new ArrayList<>();
	requestResolvers.addAll(simpleController.getRequestResolvers());
	RequestsResolver.createInstance(serverConfiguration.getContextPath(), requestResolvers);
	Server server = new Server(8080);
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
