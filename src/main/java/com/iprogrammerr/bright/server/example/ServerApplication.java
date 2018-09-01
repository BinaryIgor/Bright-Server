package com.iprogrammerr.bright.server.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.iprogrammerr.bright.server.Server;
import com.iprogrammerr.bright.server.configuration.ServerConfiguration;
import com.iprogrammerr.bright.server.constants.RequestMethod;
import com.iprogrammerr.bright.server.filter.RequestFilter;
import com.iprogrammerr.bright.server.parser.FilterUrlPatternParser;
import com.iprogrammerr.bright.server.parser.StarSymbolFilterUrlPatternParser;
import com.iprogrammerr.bright.server.parser.TypedUrlPatternParser;
import com.iprogrammerr.bright.server.parser.UrlPatternParser;
import com.iprogrammerr.bright.server.resolver.RequestResolver;
import com.iprogrammerr.bright.server.rule.AnyRequestMethodRule;
import com.iprogrammerr.bright.server.rule.ListOfRequestMethodRule;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
	ServerConfiguration serverConfiguration = new ServerConfiguration(getServerProperties());

	UrlPatternParser urlPatternParser = new TypedUrlPatternParser();
	FilterUrlPatternParser filterUrlPatternParser = new StarSymbolFilterUrlPatternParser();

	List<RequestResolver> requestResolvers = new ArrayList<>();
	RequestResolver helloResolver = new RequestResolver("hello/{id:int}", RequestMethod.GET, urlPatternParser,
		new HelloHandler());
	RequestResolver complexResolver = new RequestResolver("complex/{id:long}/search?message=string&scale=float", RequestMethod.GET,
		urlPatternParser, new ComplexUrlHandler());
	requestResolvers.add(helloResolver);
	requestResolvers.add(complexResolver);
	
	List<RequestFilter> requestFilters = new ArrayList<>();
	RequestFilter authorizationFilter = new RequestFilter("*", new AnyRequestMethodRule(), filterUrlPatternParser,
		new AuthorizationHandler());
	RequestFilter authorizationSecondFilter = new RequestFilter("hello/*",
		new ListOfRequestMethodRule(RequestMethod.GET, RequestMethod.POST), filterUrlPatternParser,
		new AuthorizationSecondFreePassHandler());
	requestFilters.add(authorizationFilter);
	requestFilters.add(authorizationSecondFilter);

	
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
