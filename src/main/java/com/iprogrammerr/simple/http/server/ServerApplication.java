package com.iprogrammerr.simple.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.iprogrammerr.simple.http.server.configuration.ServerConfiguration;
import com.iprogrammerr.simple.http.server.constants.RequestMethod;
import com.iprogrammerr.simple.http.server.example.AuthorizationHandler;
import com.iprogrammerr.simple.http.server.example.HelloHandler;
import com.iprogrammerr.simple.http.server.filter.RequestFilter;
import com.iprogrammerr.simple.http.server.parser.FilterUrlPatternParser;
import com.iprogrammerr.simple.http.server.parser.HttpOneParser;
import com.iprogrammerr.simple.http.server.parser.RequestResponseParser;
import com.iprogrammerr.simple.http.server.parser.ResolverUrlPatternParser;
import com.iprogrammerr.simple.http.server.resolver.RequestResolver;
import com.iprogrammerr.simple.http.server.rule.AnyRequestMethodRule;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
	ServerConfiguration serverConfiguration = new ServerConfiguration(getServerProperties());

	RequestResponseParser httpParser = new HttpOneParser(serverConfiguration);
	ResolverUrlPatternParser resolverUrlPatternParser = new ResolverUrlPatternParser();
	FilterUrlPatternParser filterUrlPatternParser = new FilterUrlPatternParser();

	List<RequestResolver> requestResolvers = new ArrayList<>();
	RequestResolver helloResolver = new RequestResolver("hello/{id:int}", RequestMethod.GET,
		resolverUrlPatternParser, new HelloHandler());
	requestResolvers.add(helloResolver);

	List<RequestFilter> requestFilters = new ArrayList<>();
	RequestFilter authorizationFilter = new RequestFilter("*", new AnyRequestMethodRule(), filterUrlPatternParser,
		new AuthorizationHandler());

	Server server = new Server(serverConfiguration, httpParser, requestResolvers, requestFilters);
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
