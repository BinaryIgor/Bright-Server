package com.iprogrammerr.bright.server.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.iprogrammerr.bright.server.Server;
import com.iprogrammerr.bright.server.configuration.BrightServerConfiguration;
import com.iprogrammerr.bright.server.configuration.ServerConfiguration;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilter;
import com.iprogrammerr.bright.server.filter.HttpRequestFilter;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.respondent.HttpRespondent;
import com.iprogrammerr.bright.server.rule.AnyRequestMethodRule;
import com.iprogrammerr.bright.server.rule.ListOfRequestMethodRule;

public class ServerApplication {

    public static void main(String[] args) throws IOException {
	ServerConfiguration serverConfiguration = new BrightServerConfiguration(serverProperties());

	RequestMethod get = new GetMethod();
	RequestMethod post = new PostMethod();

	List<ConditionalRespondent> respondents = new ArrayList<>();
	ConditionalRespondent helloResolver = new HttpRespondent("hello/{id:int}", get, new HelloRespondent());
	ConditionalRespondent complexResolver = new HttpRespondent(
		"complex/{id:long}/search?message=string&scale=float", post, new ComplexUrlRespondent());
	respondents.add(helloResolver);
	respondents.add(complexResolver);

	List<ConditionalRequestFilter> requestFilters = new ArrayList<>();
	ConditionalRequestFilter authorizationFilter = new HttpRequestFilter("*", new AnyRequestMethodRule(),
		new AuthorizationFilter());
	ConditionalRequestFilter authorizationSecondFilter = new HttpRequestFilter("hello/*",
		new ListOfRequestMethodRule(get, post), new AuthorizationSecondFreePassFilter());
	requestFilters.add(authorizationFilter);
	requestFilters.add(authorizationSecondFilter);

	Server server = new Server(serverConfiguration, respondents, requestFilters);
	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	    server.stop();
	}));
	server.start();
    }

    private static Properties serverProperties() throws IOException {
	InputStream inputStream = ServerApplication.class.getResourceAsStream("/server.properties");
	Properties properties = new Properties();
	properties.load(inputStream);
	inputStream.close();
	return properties;
    }
}
