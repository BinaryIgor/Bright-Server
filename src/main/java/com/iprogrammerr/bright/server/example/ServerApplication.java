package com.iprogrammerr.bright.server.example;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.Connector;
import com.iprogrammerr.bright.server.RequestResponseConnector;
import com.iprogrammerr.bright.server.Server;
import com.iprogrammerr.bright.server.cors.AllowAllCors;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilter;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilters;
import com.iprogrammerr.bright.server.filter.HttpRequestFilter;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.respondent.FileRespondent;
import com.iprogrammerr.bright.server.respondent.HttpRespondent;
import com.iprogrammerr.bright.server.rule.AnyRequestMethodRule;
import com.iprogrammerr.bright.server.rule.ListOfRequestMethodRule;

public class ServerApplication {

    public static void main(String[] args) throws Exception {
	RequestMethod get = new GetMethod();
	RequestMethod post = new PostMethod();

	List<ConditionalRespondent> respondents = new ArrayList<>();
	ConditionalRespondent helloRespondent = new HttpRespondent("hello/{id:int}", get, new HelloRespondent());
	ConditionalRespondent complexRespondent = new HttpRespondent(
		"complex/{id:long}/search?message=string&scale=float", post, new ComplexUrlRespondent());
	ConditionalRespondent fileRespondent = new FileRespondent();
	respondents.add(helloRespondent);
	respondents.add(complexRespondent);
	respondents.add(fileRespondent);

	List<ConditionalRequestFilter> filters = new ArrayList<>();
	ConditionalRequestFilter authorizationFilter = new HttpRequestFilter("*", new AnyRequestMethodRule(),
		new AuthorizationFilter());
	ConditionalRequestFilter authorizationSecondFilter = new HttpRequestFilter("hello/*",
		new ListOfRequestMethodRule(get, post), new AuthorizationSecondFreePassFilter());
	filters.add(authorizationFilter);
	filters.add(authorizationSecondFilter);

	Connector connection = new RequestResponseConnector("example", new AllowAllCors(), respondents,
		new ConditionalRequestFilters(filters));

	Server server = new Server(8080, 5000, connection);
	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	    server.stop();
	}));
	server.start();
    }

}
