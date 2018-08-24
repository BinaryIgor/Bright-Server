package com.iprogrammerr.simpleserver.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.iprogrammerr.simpleserver.constants.ResponseCode;
import com.iprogrammerr.simpleserver.exception.InitializationException;
import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;
import com.iprogrammerr.simpleserver.parser.RequestParser;
import com.iprogrammerr.simpleserver.resolver.RequestsResolver;

public class Server {

    private static final String NEW_LINE = "\n";
    private static final String HEADERS_BODY_SEPARATOR = "\r\n\r\n";
    private ServerSocket serverSocket;
    private Executor executor = Executors.newCachedThreadPool();
    private RequestParser httpParser = new RequestParser();
    private boolean stopped;

    public Server(int port) {
	try {
	    this.serverSocket = new ServerSocket(port);
	} catch (IOException exception) {
	    throw new InitializationException(exception);
	}
    }

    public void start() {
	while (!isStopped()) {
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
		Request request = httpParser.getRequest(inputStream);
		System.out.println(request);
		Response response = RequestsResolver.getInstance().resolve(request);
		writeResponse(outputStream, response);
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

    private void writeResponse(BufferedOutputStream outputStream, Response response) throws IOException {
	String stringedResponse = getResponse(response);
	byte[] rawResponse = stringedResponse.getBytes();
	outputStream.write(rawResponse);
    }

    private String getResponse(Response response) {
	StringBuilder builder = new StringBuilder();
	builder.append(responseCodeToString(response.getCode())).append(NEW_LINE)
		.append("Access-Control-Allow-Origin: *");
	if (response.hasBody()) {
	    builder.append(HEADERS_BODY_SEPARATOR).append(response.getBody());
	}
	return builder.toString();
    }

    private String responseCodeToString(ResponseCode responseCode) {
	return "HTTP/1.1 " + responseCode.getValue();
    }

    private synchronized boolean isStopped() {
	return stopped;
    }

    public void stop() {
	stopped = true;
	try {
	    serverSocket.close();
	} catch (IOException exception) {
	    exception.printStackTrace();
	}
    }
}
