package com.iprogrammerr.simpleserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.iprogrammerr.simpleserver.exception.InitializationException;
import com.iprogrammerr.simpleserver.parser.RequestParser;

public class Server {

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
		Socket clientSocket = serverSocket.accept();
		executor.execute(new RequestHandler(clientSocket, httpParser));
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }
	}
    }

    private synchronized boolean isStopped() {
	return stopped;
    }

    private String getAcceptedResponse() {
	return "HTTP/1.1 200 OK";
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
