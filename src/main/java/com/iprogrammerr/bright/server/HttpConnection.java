package com.iprogrammerr.bright.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.configuration.ServerConfiguration;
import com.iprogrammerr.bright.server.exception.ObjectNotFoundException;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilter;
import com.iprogrammerr.bright.server.method.OptionsMethod;
import com.iprogrammerr.bright.server.protocol.RequestResponseProtocol;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.response.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.InternalServerErrorResponse;
import com.iprogrammerr.bright.server.response.NotFoundResponse;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class HttpConnection implements Connection {

    private static final OptionsMethod OPTIONS_METHOD = new OptionsMethod();
    private static final String CONNECTION_HEADER = "Connection";
    private static final String CONNECTION_CLOSE = "close";
    private final ServerConfiguration serverConfiguration;
    private final RequestResponseProtocol protocol;
    private final List<ConditionalRespondent> respondents;
    private final List<ConditionalRequestFilter> primaryRequestFilters;
    private final List<ConditionalRequestFilter> requestFilters;

    public HttpConnection(ServerConfiguration serverConfiguration, RequestResponseProtocol protocol,
	    List<ConditionalRespondent> respondents, List<ConditionalRequestFilter> primaryRequestFilters,
	    List<ConditionalRequestFilter> requestFilters) {
	this.serverConfiguration = serverConfiguration;
	this.protocol = protocol;
	this.respondents = respondents;
	this.primaryRequestFilters = primaryRequestFilters;
	this.requestFilters = requestFilters;
    }

    @Override
    public Runnable handle(Socket socket) {
	return () -> {
	    try (InputStream inputStream = socket.getInputStream();
		    OutputStream outputStream = socket.getOutputStream()) {
		socket.setSoTimeout(serverConfiguration.timeout());
		Request request = protocol.read(inputStream);
		System.out.println(request);
		Response response = respond(request);
		protocol.write(outputStream, response);
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
	    if (OPTIONS_METHOD.is(request.method())) {
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

}
