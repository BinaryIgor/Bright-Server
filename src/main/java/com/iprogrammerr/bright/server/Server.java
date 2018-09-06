package com.iprogrammerr.bright.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.iprogrammerr.bright.server.configuration.ServerConfiguration;
import com.iprogrammerr.bright.server.exception.ObjectNotFoundException;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilter;
import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.protocol.HttpOneProtocol;
import com.iprogrammerr.bright.server.protocol.RequestResponseProtocol;
import com.iprogrammerr.bright.server.request.ConfigurableCors;
import com.iprogrammerr.bright.server.request.Cors;
import com.iprogrammerr.bright.server.request.OptionsMethod;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.response.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.InternalServerErrorResponse;
import com.iprogrammerr.bright.server.response.NotFoundResponse;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class Server {

    private ServerSocket serverSocket;
    private Executor executor;
    private ServerConfiguration serverConfiguration;
    private RequestResponseProtocol protocol;
    private List<ConditionalRespondent> respondents;
    private List<ConditionalRequestFilter> primaryRequestFilters;
    private List<ConditionalRequestFilter> requestFilters;
    private Cors cors;

    public Server(ServerConfiguration serverConfiguration, Executor executor, RequestResponseProtocol protocol,
	    List<ConditionalRespondent> respondents, List<ConditionalRequestFilter> requestFilters, Cors cors)
	    throws IOException {
	this.serverSocket = new ServerSocket(serverConfiguration.port());
	this.executor = executor;
	this.protocol = protocol;
	this.respondents = respondents;
	this.primaryRequestFilters = requestFilters.stream().filter(requestFilter -> requestFilter.isPrimary())
		.collect(Collectors.toList());
	this.requestFilters = requestFilters.stream().filter(requestFilter -> !requestFilter.isPrimary())
		.collect(Collectors.toList());
	this.serverConfiguration = serverConfiguration;
	this.cors = cors;
    }

    public Server(ServerConfiguration serverConfiguration, RequestResponseProtocol requestReponseParser,
	    List<ConditionalRespondent> respondents, List<ConditionalRequestFilter> requestFilters) throws IOException {
	this(serverConfiguration, Executors.newCachedThreadPool(), requestReponseParser, respondents, requestFilters,
		new ConfigurableCors(serverConfiguration));
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters) throws IOException {
	this(serverConfiguration, Executors.newCachedThreadPool(),
		new HttpOneProtocol(serverConfiguration.corsHeaders()), respondents, requestFilters,
		new ConfigurableCors(serverConfiguration));
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents) throws IOException {
	this(serverConfiguration, Executors.newCachedThreadPool(),
		new HttpOneProtocol(serverConfiguration.corsHeaders()), respondents, new ArrayList<>(),
		new ConfigurableCors(serverConfiguration));
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters, List<Header> additionalResponeHeaders) throws IOException {
	this(serverConfiguration, Executors.newCachedThreadPool(),
		new HttpOneProtocol(serverConfiguration, additionalResponeHeaders), respondents, requestFilters,
		new ConfigurableCors(serverConfiguration));
    }

    public Server(ServerConfiguration serverConfiguration, Executor executor, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters) throws IOException {
	this(serverConfiguration, executor, new HttpOneProtocol(serverConfiguration), respondents, requestFilters,
		new ConfigurableCors(serverConfiguration));
    }

    public Server(ServerConfiguration serverConfiguration, Executor executor, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters, List<Header> additionalResponseHeaders) throws IOException {
	this(serverConfiguration, executor, new HttpOneProtocol(serverConfiguration, additionalResponseHeaders),
		respondents, requestFilters, new ConfigurableCors(serverConfiguration));
    }

    public void start() {
	System.out.println("Bright Server is shining!");
	while (!serverSocket.isClosed()) {
	    try {
		Socket socket = serverSocket.accept();
		executor.execute(handleConnection(socket));
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }
	}
    }

    private Runnable handleConnection(Socket socket) {
	return () -> {
	    try (InputStream inputStream = socket.getInputStream();
		    OutputStream outputStream = socket.getOutputStream()) {
		socket.setSoTimeout(serverConfiguration.timeOutMillis());
		Request request = protocol.read(inputStream);
		Response response = respond(request);
		protocol.write(outputStream, response);
	    } catch (Exception exception) {
		exception.printStackTrace();
	    } finally {
		try {
		    socket.close();
		} catch (IOException exception) {
		    exception.printStackTrace();
		}
	    }
	};
    }

    public Response respond(Request request) {
	if (!request.url().startsWith(serverConfiguration.contextPath())) {
	    return new NotFoundResponse();
	}
	try {
	    if (serverConfiguration.addCorsHeaders() && new OptionsMethod().is(request.method())) {
		return handleOptionsRequest(request);
	    }
	    request.removeContextPath(serverConfiguration.contextPath());
	    ConditionalRespondent respondent = findRespondent(request);
	    Response response = filter(request);
	    if (!isResponseCodeOk(response.responseCode())) {
		return response;
	    }
	    return respondent.respond(request);
	} catch (ObjectNotFoundException exception) {
	    exception.printStackTrace();
	    return new NotFoundResponse();
	} catch (Exception exception) {
	    exception.printStackTrace();
	    return new InternalServerErrorResponse();
	}
    }

    private ConditionalRespondent findRespondent(Request request) throws Exception {
	for (ConditionalRespondent respondent : respondents) {
	    if (respondent.canRespond(request)) {
		return respondent;
	    }
	}
	throw new ObjectNotFoundException();
    }

    private Response filter(Request request) throws Exception {
	Response response = runFilters(request, primaryRequestFilters);
	if (!isResponseCodeOk(response.responseCode())) {
	    return response;
	}
	return runFilters(request, getFilters(request));
    }

    private Response runFilters(Request request, List<ConditionalRequestFilter> requestFilters) throws Exception {
	Response response = new OkResponse();
	for (ConditionalRequestFilter filter : requestFilters) {
	    response = filter.filter(request);
	    if (!isResponseCodeOk(response.responseCode())) {
		return response;
	    }
	}
	return response;
    }

    private List<ConditionalRequestFilter> getFilters(Request request) {
	List<ConditionalRequestFilter> matchedFilters = new ArrayList<>();
	for (ConditionalRequestFilter filter : requestFilters) {
	    if (filter.canFilter(request)) {
		matchedFilters.add(filter);
	    }
	}
	return matchedFilters;
    }

    private Response handleOptionsRequest(Request request) {
	if (!cors.validate(request)) {
	    return new ForbiddenResponse();
	}
	return new OkResponse();
    }

    private boolean isResponseCodeOk(int responseCode) {
	return responseCode >= 200 && responseCode < 300;
    }

    public void stop() {
	try {
	    serverSocket.close();
	    System.out.println("Bright Server is fading away...");
	} catch (IOException exception) {
	    exception.printStackTrace();
	}
    }
}
