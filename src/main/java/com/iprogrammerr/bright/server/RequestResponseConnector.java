package com.iprogrammerr.bright.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import com.iprogrammerr.bright.server.cors.Cors;
import com.iprogrammerr.bright.server.exception.NotFoundException;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilters;
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

public class RequestResponseConnector implements Connector {

    private static final OptionsMethod OPTIONS_METHOD = new OptionsMethod();
    private static final String CONNECTION_HEADER = "Connection";
    private static final String CONNECTION_CLOSE = "close";
    private final String contextPath;
    private final Cors cors;
    private final RequestResponseProtocol protocol;
    private final List<ConditionalRespondent> respondents;
    private final ConditionalRequestFilters filters;

    public RequestResponseConnector(String contextPath, Cors cors, RequestResponseProtocol protocol,
	    List<ConditionalRespondent> respondents, ConditionalRequestFilters filters) {
	this.contextPath = contextPath;
	this.cors = cors;
	this.protocol = protocol;
	this.respondents = respondents;
	this.filters = filters;
    }

    public RequestResponseConnector(String contextPath, Cors cors, List<ConditionalRespondent> respondents,
	    ConditionalRequestFilters filters) {
	this(contextPath, cors, new HttpOneProtocol(cors.toAddHeaders()), respondents, filters);
    }

    public RequestResponseConnector(String contextPath, Cors cors, List<ConditionalRespondent> respondents) {
	this(contextPath, cors, new HttpOneProtocol(cors.toAddHeaders()), respondents, new ConditionalRequestFilters());
    }

    public RequestResponseConnector(Cors cors, List<ConditionalRespondent> respondents,
	    ConditionalRequestFilters filters) {
	this("", cors, new HttpOneProtocol(cors.toAddHeaders()), respondents, filters);
    }

    public RequestResponseConnector(Cors cors, List<ConditionalRespondent> respondents) {
	this("", cors, new HttpOneProtocol(cors.toAddHeaders()), respondents, new ConditionalRequestFilters());
    }

    @Override
    public Runnable plug(Socket socket) {
	return () -> {
	    try (InputStream inputStream = socket.getInputStream();
		    OutputStream outputStream = socket.getOutputStream()) {
		Request request = protocol.read(inputStream);
		Response response = respond(request);
		protocol.write(outputStream, response);
		boolean closeConnection = !request.hasHeader(CONNECTION_HEADER)
			|| request.header(CONNECTION_HEADER).equalsIgnoreCase(CONNECTION_CLOSE);
		if (closeConnection) {
		    socket.close();
		}
	    } catch (Exception exception) {
		exception.printStackTrace();
		closeConnection(socket);
	    }
	};
    }

    private void closeConnection(Socket socket) {
	try {
	    socket.close();
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    private Response respond(Request request) {
	if (!request.url().startsWith(contextPath)) {
	    return new NotFoundResponse();
	}
	if (OPTIONS_METHOD.is(request.method())) {
	    return respondToOptions(request);
	}
	try {
	    request.removeContextPath(contextPath);
	    ConditionalRespondent respondent = findRespondent(request);
	    Response response = filters.run(request);
	    if (!isResponseCodeOk(response.responseCode())) {
		return response;
	    }
	    return respondent.respond(request);
	} catch (NotFoundException exception) {
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
	throw new NotFoundException(
		String.format("There is no respondent for %s method and url: %s", request.method(), request.url()));
    }

    private Response respondToOptions(Request request) {
	if (!cors.validate(request)) {
	    return new ForbiddenResponse();
	}
	return new OkResponse();
    }

    private boolean isResponseCodeOk(int responseCode) {
	return responseCode >= 200 && responseCode < 300;
    }

}
