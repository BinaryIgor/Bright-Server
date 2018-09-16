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
import com.iprogrammerr.bright.server.loading.UnreliableLoading;
import com.iprogrammerr.bright.server.loading.UnreliableStickyLoading;
import com.iprogrammerr.bright.server.method.OptionsMethod;
import com.iprogrammerr.bright.server.protocol.HttpOneProtocol;
import com.iprogrammerr.bright.server.protocol.RequestResponseProtocol;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.response.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.InternalServerErrorResponse;
import com.iprogrammerr.bright.server.response.NotFoundResponse;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class Server {

    private static final String CONNECTION_HEADER = "Connection";
    private static final String CONNECTION_CLOSE = "close";
    private final UnreliableLoading<ServerSocket> serverSocket;
    private final Executor executor;
    private final ServerConfiguration serverConfiguration;
    private final RequestResponseProtocol protocol;
    private final List<ConditionalRespondent> respondents;
    private final List<ConditionalRequestFilter> primaryRequestFilters;
    private final List<ConditionalRequestFilter> requestFilters;
    private final OptionsMethod optionsMethod;

    public Server(ServerConfiguration serverConfiguration, Executor executor, RequestResponseProtocol protocol,
	    List<ConditionalRespondent> respondents, List<ConditionalRequestFilter> requestFilters) {
	this.serverSocket = new UnreliableStickyLoading<>(() -> {
	    return new ServerSocket(serverConfiguration.port());
	});
	this.serverConfiguration = serverConfiguration;
	this.executor = executor;
	this.protocol = protocol;
	this.respondents = respondents;
	this.primaryRequestFilters = requestFilters.stream().filter(requestFilter -> requestFilter.isPrimary())
		.collect(Collectors.toList());
	this.requestFilters = requestFilters.stream().filter(requestFilter -> !requestFilter.isPrimary())
		.collect(Collectors.toList());
	this.optionsMethod = new OptionsMethod();
    }

    public Server(ServerConfiguration serverConfiguration, RequestResponseProtocol requestReponseProtocol,
	    List<ConditionalRespondent> respondents, List<ConditionalRequestFilter> requestFilters) {
	this(serverConfiguration, Executors.newCachedThreadPool(), requestReponseProtocol, respondents, requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters) throws IOException {
	this(serverConfiguration, Executors.newCachedThreadPool(),
		new HttpOneProtocol(serverConfiguration.cors().toAddHeaders()), respondents, requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents) {
	this(serverConfiguration, Executors.newCachedThreadPool(),
		new HttpOneProtocol(serverConfiguration.cors().toAddHeaders()), respondents, new ArrayList<>());
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters, List<Header> additionalResponeHeaders) {
	this(serverConfiguration, Executors.newCachedThreadPool(),
		new HttpOneProtocol(serverConfiguration.cors().toAddHeaders()), respondents, requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, Executor executor, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters) throws IOException {
	this(serverConfiguration, executor, new HttpOneProtocol(serverConfiguration.cors().toAddHeaders()), respondents,
		requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, Executor executor, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters, List<Header> additionalResponseHeaders) {
	this(serverConfiguration, executor, new HttpOneProtocol(serverConfiguration.cors().toAddHeaders()), respondents,
		requestFilters);
    }

    public void start() throws Exception {
	System.out.println("Bright Server is shining!");
	ServerSocket serverSocket = this.serverSocket.load();
	while (!serverSocket.isClosed()) {
	    try {
		Socket socket = serverSocket.accept();
		executor.execute(handleConnection(socket));
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }
	}
    }

    /// TODO object!
    private Runnable handleConnection(Socket socket) {
	return () -> {
	    try (InputStream inputStream = socket.getInputStream();
		    OutputStream outputStream = socket.getOutputStream()) {
		socket.setSoTimeout(serverConfiguration.timeout());
		Request request = protocol.read(inputStream);
		System.out.println(request);
		Response response = respond(request);
		protocol.write(outputStream, response);
		outputStream.flush();
		closeConnectionIfNeeded(request, socket);
	    } catch (Exception exception) {
		exception.printStackTrace();
		closeConnection(socket);
	    }
	};
    }

    private void closeConnectionIfNeeded(Request request, Socket socket) throws Exception {
	boolean closeConnection = !request.hasHeader(CONNECTION_HEADER)
		|| request.header(CONNECTION_HEADER).equalsIgnoreCase(CONNECTION_CLOSE);
	if (closeConnection) {
	    socket.close();
	}
    }

    private void closeConnection(Socket socket) {
	try {
	    socket.close();
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    public Response respond(Request request) {
	if (!request.url().startsWith(serverConfiguration.contextPath())) {
	    return new NotFoundResponse();
	}
	try {
	    if (optionsMethod.is(request.method())) {
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
	    if (respondent.conditionsMet(request)) {
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
	if (!serverConfiguration.cors().validate(request)) {
	    return new ForbiddenResponse();
	}
	return new OkResponse();
    }

    private boolean isResponseCodeOk(int responseCode) {
	return responseCode >= 200 && responseCode < 300;
    }

    public void stop() {
	try {
	    serverSocket.load().close();
	    System.out.println("Bright Server is fading away...");
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }
}
