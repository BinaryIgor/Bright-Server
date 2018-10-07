package com.iprogrammerr.bright.server.example;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.Connection;
import com.iprogrammerr.bright.server.RequestResponseConnection;
import com.iprogrammerr.bright.server.Server;
import com.iprogrammerr.bright.server.application.Application;
import com.iprogrammerr.bright.server.application.HttpApplication;
import com.iprogrammerr.bright.server.cors.AllowAllCors;
import com.iprogrammerr.bright.server.filter.ConditionalFilter;
import com.iprogrammerr.bright.server.filter.PotentialFilter;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.protocol.HttpOneProtocol;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.respondent.PotentialRespondent;
import com.iprogrammerr.bright.server.rule.method.AnyRequestMethodRule;
import com.iprogrammerr.bright.server.rule.method.ListOfRequestMethodRule;

public final class SimpleApplication {

    public static void main(String[] args) throws Exception {
	RequestMethod get = new GetMethod();
	RequestMethod post = new PostMethod();

	List<ConditionalRespondent> respondents = new ArrayList<>();
	ConditionalRespondent helloRespondent = new PotentialRespondent("hello/{id:int}", get, new HelloRespondent());
	ConditionalRespondent complexRespondent = new PotentialRespondent(
		"complex/{id:long}/search?message=string&scale=float", post, new ComplexUrlRespondent());
	respondents.add(helloRespondent);
	respondents.add(complexRespondent);

	List<ConditionalFilter> filters = new ArrayList<>();
	ConditionalFilter authorizationFilter = new PotentialFilter("*", new AnyRequestMethodRule(),
		new AuthorizationFilter());
	ConditionalFilter authorizationSecondFilter = new PotentialFilter("hello/*",
		new ListOfRequestMethodRule(get, post), new AuthorizationSecondFreePassFilter());
	filters.add(authorizationFilter);
	filters.add(authorizationSecondFilter);

	Application application = new HttpApplication(new AllowAllCors(), respondents, filters);

	Connection connection = new RequestResponseConnection(new HttpOneProtocol(), application);

	Server server = new Server(8080, 5000, connection);
	server.start();
    }
}
