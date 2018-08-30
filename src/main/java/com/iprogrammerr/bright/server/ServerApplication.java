package com.iprogrammerr.bright.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.iprogrammerr.bright.server.configuration.ServerConfiguration;
import com.iprogrammerr.bright.server.constants.RequestMethod;
import com.iprogrammerr.bright.server.example.AuthorizationHandler;
import com.iprogrammerr.bright.server.example.HelloHandler;
import com.iprogrammerr.bright.server.filter.RequestFilter;
import com.iprogrammerr.bright.server.parser.FilterUrlPatternParser;
import com.iprogrammerr.bright.server.parser.ResolverUrlPatternParser;
import com.iprogrammerr.bright.server.resolver.RequestResolver;
import com.iprogrammerr.bright.server.rule.AnyRequestMethodRule;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
	ServerConfiguration serverConfiguration = new ServerConfiguration(getServerProperties());

	ResolverUrlPatternParser resolverUrlPatternParser = new ResolverUrlPatternParser();
	FilterUrlPatternParser filterUrlPatternParser = new FilterUrlPatternParser();

	List<RequestResolver> requestResolvers = new ArrayList<>();
	RequestResolver helloResolver = new RequestResolver("hello/{id:int}", RequestMethod.GET,
		resolverUrlPatternParser, new HelloHandler());
	requestResolvers.add(helloResolver);

	List<RequestFilter> requestFilters = new ArrayList<>();
	RequestFilter authorizationFilter = new RequestFilter("*", new AnyRequestMethodRule(), filterUrlPatternParser,
		new AuthorizationHandler());
	requestFilters.add(authorizationFilter);

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