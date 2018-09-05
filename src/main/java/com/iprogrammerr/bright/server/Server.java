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
import com.iprogrammerr.bright.server.constants.HeaderKey;
import com.iprogrammerr.bright.server.constants.RequestMethod;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.exception.InitializationException;
import com.iprogrammerr.bright.server.exception.ObjectNotFoundException;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilter;
import com.iprogrammerr.bright.server.header.HttpHeader;
import com.iprogrammerr.bright.server.protocol.HttpOneProtocol;
import com.iprogrammerr.bright.server.protocol.RequestResponseProtocol;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.Response;

public class Server {

    private static final String ALLOW_ALL = "*";
    private ServerSocket serverSocket;
    private Executor executor;
    private ServerConfiguration serverConfiguration;
    private RequestResponseProtocol protocol;
    private List<ConditionalRespondent> respondents;
    private List<ConditionalRequestFilter> primaryRequestFilters;
    private List<ConditionalRequestFilter> requestFilters;

    public Server(ServerConfiguration serverConfiguration, Executor executor,
	    RequestResponseProtocol protocol, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters) {
	try {
	    this.serverSocket = new ServerSocket(serverConfiguration.port());
	} catch (IOException exception) {
	    throw new InitializationException(exception);
	}
	this.executor = executor;
	this.protocol = protocol;
	this.respondents = respondents;
	this.primaryRequestFilters = requestFilters.stream().filter(requestFilter -> requestFilter.isPrimary())
		.collect(Collectors.toList());
	this.requestFilters = requestFilters.stream().filter(requestFilter -> !requestFilter.isPrimary())
		.collect(Collectors.toList());
	this.serverConfiguration = serverConfiguration;
    }

    public Server(ServerConfiguration serverConfiguration, RequestResponseProtocol requestReponseParser,
	    List<ConditionalRespondent> respondents, List<ConditionalRequestFilter> requestFilters) {
	this(serverConfiguration, Executors.newCachedThreadPool(), requestReponseParser, respondents,
		requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters) {
	this(serverConfiguration, Executors.newCachedThreadPool(), new HttpOneProtocol(serverConfiguration),
		respondents, requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents) {
	this(serverConfiguration, Executors.newCachedThreadPool(), new HttpOneProtocol(serverConfiguration),
		respondents, new ArrayList<>());
    }

    public Server(ServerConfiguration serverConfiguration, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters, List<HttpHeader> additionalResponeHeaders) {
	this(serverConfiguration, Executors.newCachedThreadPool(),
		new HttpOneProtocol(serverConfiguration, additionalResponeHeaders), respondents, requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, Executor executor, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters) {
	this(serverConfiguration, executor, new HttpOneProtocol(serverConfiguration), respondents, requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, Executor executor, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> requestFilters, List<HttpHeader> additionalResponseHeaders) {
	this(serverConfiguration, executor, new HttpOneProtocol(serverConfiguration, additionalResponseHeaders),
		respondents, requestFilters);
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
	    } catch (IOException exception) {
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
	    return new EmptyResponse(ResponseCode.NOT_FOUND);
	}
	try {
	    if (RequestMethod.OPTIONS.equalsByValue(request.method()) && serverConfiguration.addCorsHeaders()) {
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
	    return new EmptyResponse(ResponseCode.NOT_FOUND);
	} catch (Exception exception) {
	    return new EmptyResponse(ResponseCode.INTERNAL_SERVER_ERROR);
	}
    }

    private ConditionalRespondent findRespondent(Request request) {
	for (ConditionalRespondent respondent : respondents) {
	    if (respondent.canRespond(request)) {
		return respondent;
	    }
	}
	throw new ObjectNotFoundException();
    }

    private Response filter(Request request) {
	Response response = runFilters(request, primaryRequestFilters);
	if (!isResponseCodeOk(response.responseCode())) {
	    return response;
	}
	return runFilters(request, getFilters(request));
    }

    private Response runFilters(Request request, List<ConditionalRequestFilter> requestFilters) {
	Response response = new EmptyResponse(ResponseCode.OK);
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
	boolean haveRequiredHeaders = request.hasHeader(HeaderKey.ORIGIN.getValue())
		&& request.hasHeader(HeaderKey.ACCESS_CONTROL_REQUEST_METHOD.getValue())
		&& request.hasHeader(HeaderKey.ACCESS_CONTROL_REQUEST_HEADERS.getValue());
	if (!haveRequiredHeaders) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	boolean originAllowed = serverConfiguration.allowedOrigin().equals(ALLOW_ALL)
		|| serverConfiguration.allowedOrigin().equals(request.header(HeaderKey.ORIGIN.getValue()));
	if (!originAllowed) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	boolean methodAllowed = serverConfiguration.allowedMethods().equals(ALLOW_ALL) || serverConfiguration
		.allowedMethods().contains(request.header(HeaderKey.ACCESS_CONTROL_REQUEST_METHOD.getValue()));
	if (!methodAllowed) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	boolean headersAllowed = serverConfiguration.allowedHeaders().equals(ALLOW_ALL) || serverConfiguration
		.allowedHeaders().contains(request.header(HeaderKey.ACCESS_CONTROL_REQUEST_METHOD.getValue()));
	if (!headersAllowed) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	return new EmptyResponse(ResponseCode.OK);
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
