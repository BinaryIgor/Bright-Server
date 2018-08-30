package com.iprogrammerr.bright.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.iprogrammerr.bright.server.configuration.ServerConfiguration;
import com.iprogrammerr.bright.server.constants.RequestHeaderKey;
import com.iprogrammerr.bright.server.constants.RequestMethod;
import com.iprogrammerr.bright.server.constants.ResponseCode;
import com.iprogrammerr.bright.server.exception.InitializationException;
import com.iprogrammerr.bright.server.exception.ObjectNotFoundException;
import com.iprogrammerr.bright.server.filter.RequestFilter;
import com.iprogrammerr.bright.server.model.Request;
import com.iprogrammerr.bright.server.parser.HttpOneParser;
import com.iprogrammerr.bright.server.parser.RequestResponseParser;
import com.iprogrammerr.bright.server.resolver.RequestResolver;
import com.iprogrammerr.bright.server.response.EmptyResponse;
import com.iprogrammerr.bright.server.response.Response;

public class Server {

    private static final String ALLOW_ALL = "*";
    private ServerSocket serverSocket;
    private Executor executor;
    private String contextPath;
    private ServerConfiguration serverConfiguration;
    private RequestResponseParser requestResponseParser;
    private List<RequestResolver> requestResolvers;
    private List<RequestFilter> primaryRequestFilters;
    private List<RequestFilter> requestFilters;

    public Server(ServerConfiguration serverConfiguration, Executor executor,
	    RequestResponseParser requestReponseParser, List<RequestResolver> requestsResolvers,
	    List<RequestFilter> requestFilters) {
	try {
	    this.serverSocket = new ServerSocket(serverConfiguration.getPort());
	} catch (IOException exception) {
	    throw new InitializationException(exception);
	}
	this.executor = executor;
	this.contextPath = serverConfiguration.getContextPath();
	this.requestResponseParser = requestReponseParser;
	this.requestResolvers = requestsResolvers;
	this.primaryRequestFilters = new ArrayList<>();
	this.requestFilters = requestFilters;
	this.serverConfiguration = serverConfiguration;
    }

    public Server(ServerConfiguration serverConfiguration, List<RequestResolver> requestsResolvers,
	    List<RequestFilter> requestFilters) {
	this(serverConfiguration, Executors.newCachedThreadPool(), new HttpOneParser(serverConfiguration),
		requestsResolvers, requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, Executor executor, List<RequestResolver> requestsResolvers,
	    List<RequestFilter> requestFilters) {
	this(serverConfiguration, executor, new HttpOneParser(serverConfiguration), requestsResolvers, requestFilters);
    }

    public Server(ServerConfiguration serverConfiguration, RequestResponseParser requestReponseParser,
	    List<RequestResolver> requestsResolvers, List<RequestFilter> requestFilters) {
	this(serverConfiguration, Executors.newCachedThreadPool(), requestReponseParser, requestsResolvers,
		requestFilters);
    }

    public void start() {
	while (!serverSocket.isClosed()) {
	    try {
		System.out.println("Waiting for connection...");
		Socket socket = serverSocket.accept();
		executor.execute(getRequestHandler(socket));
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }
	}
    }

    private Runnable getRequestHandler(Socket socket) {
	return () -> {
	    try (InputStream inputStream = socket.getInputStream();
		    BufferedOutputStream outputStream = new BufferedOutputStream(socket.getOutputStream())) {
		Request request = requestResponseParser.read(inputStream);
		Response response = resolve(request);
		byte[] rawResponse = requestResponseParser.write(response);
		outputStream.write(rawResponse);
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

    public Response resolve(Request request) {
	if (!request.getPath().startsWith(contextPath)) {
	    return new EmptyResponse();
	}
	try {
	    if (RequestMethod.OPTIONS.equalsByValue(request.getMethod())) {
		return handleOptionsRequest(request);
	    }
	    request.removeContextFromPath(contextPath);
	    RequestResolver resolver = getResolver(request);
	    List<RequestFilter> filters = getFilters(request);
	    for (RequestFilter filter : filters) {
		Response response = filter.filter(request);
		if (!response.getResponseCode().isOk()) {
		    return response;
		}
	    }
	    return resolver.handle(request);
	} catch (ObjectNotFoundException exception) {
	    exception.printStackTrace();
	    return new EmptyResponse();
	} catch (Exception exception) {
	    exception.printStackTrace();
	    return new EmptyResponse(ResponseCode.INTERNAL_SERVER_ERROR);
	}
    }

    private RequestResolver getResolver(Request request) {
	for (RequestResolver resolver : requestResolvers) {
	    if (resolver.canResolve(request)) {
		return resolver;
	    }
	}
	throw new ObjectNotFoundException();
    }

    private List<RequestFilter> getFilters(Request request) {
	if (primaryRequestFilters.isEmpty()) {
	    setPrimaryFilters();
	}
	List<RequestFilter> matchedFilters = new ArrayList<>();
	matchedFilters.addAll(primaryRequestFilters);
	for (RequestFilter filter : requestFilters) {
	    if (filter.shouldFilter(request)) {
		matchedFilters.add(filter);
	    }
	}
	return matchedFilters;
    }

    private void setPrimaryFilters() {
	for (RequestFilter filter : requestFilters) {
	    if (filter.isPrimary()) {
		primaryRequestFilters.add(filter);
	    }
	}
    }

    // TODO is it enough?
    private Response handleOptionsRequest(Request request) {
	boolean haveRequiredHeaders = request.hasHeader(RequestHeaderKey.ORIGIN)
		&& request.hasHeader(RequestHeaderKey.ACCESS_CONTROL_REQUEST_METHOD)
		&& request.hasHeader(RequestHeaderKey.ACCESS_CONTROL_REQUEST_HEADERS);
	if (!haveRequiredHeaders) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	boolean originAllowed = serverConfiguration.getAllowedOrigins().equals(ALLOW_ALL)
		|| serverConfiguration.getAllowedOrigins().contains(request.getHeader(RequestHeaderKey.ORIGIN));
	if (!originAllowed) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	boolean methodAllowed = serverConfiguration.getAllowedMethods().equals(ALLOW_ALL) || serverConfiguration
		.getAllowedMethods().contains(request.getHeader(RequestHeaderKey.ACCESS_CONTROL_REQUEST_METHOD));
	if (!methodAllowed) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	boolean headersAllowed = serverConfiguration.getAllowedHeaders().equals(ALLOW_ALL) || serverConfiguration
		.getAllowedHeaders().contains(request.getHeader(RequestHeaderKey.ACCESS_CONTROL_REQUEST_METHOD));
	if (!headersAllowed) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	return new EmptyResponse(ResponseCode.OK);
    }

    public String getContextPath() {
	return contextPath;
    }

    public void stop() {
	try {
	    serverSocket.close();
	} catch (IOException exception) {
	    exception.printStackTrace();
	}
    }
}
