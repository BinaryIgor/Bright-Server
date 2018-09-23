package com.iprogrammerr.bright.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.iprogrammerr.bright.server.application.Application;
import com.iprogrammerr.bright.server.protocol.RequestResponseProtocol;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;

public final class RequestResponseConnector implements Connector {

    private final RequestResponseProtocol protocol;
    private final List<Application> applications;

    public RequestResponseConnector(RequestResponseProtocol protocol, List<Application> applications) {
	this.protocol = protocol;
	this.applications = applications;
    }

    public RequestResponseConnector(RequestResponseProtocol protocol, Application application) {
	this(protocol, Collections.singletonList(application));
    }

    @Override
    public Runnable plug(Socket socket) {
	return () -> {
	    try (InputStream inputStream = socket.getInputStream();
		    OutputStream outputStream = socket.getOutputStream()) {
		Request request = protocol.read(inputStream);
		Response response = respond(request);
		protocol.write(outputStream, response);
		if (protocol.closeConnection(request)) {
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
	for (Application application : applications) {
	    Optional<Response> response = application.respond(request);
	    if (response.isPresent()) {
		return response.get();
	    }
	}
	return new NotFoundResponse();
    }

}
