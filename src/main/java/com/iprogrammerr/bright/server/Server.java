package com.iprogrammerr.bright.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.iprogrammerr.bright.server.loading.UnreliableLoading;
import com.iprogrammerr.bright.server.loading.UnreliableStickyLoading;

public final class Server {

    private final int timeout;
    private final UnreliableLoading<ServerSocket> serverSocket;
    private final Executor executor;
    private final Connector connector;

    public Server(UnreliableLoading<ServerSocket> serverSocket, int port, int timeout, Executor executor,
	    Connector connector) {
	this.serverSocket = serverSocket;
	this.timeout = timeout;
	this.executor = executor;
	this.connector = connector;
    }

    public Server(int port, int timeout, Executor executor, Connector connector) {
	this(new UnreliableStickyLoading<>(() -> {
	    return new ServerSocket(port);
	}), port, timeout, executor, connector);
    }

    public Server(int port, int timeout, Connector connector) {
	this(port, timeout, Executors.newCachedThreadPool(), connector);
    }

    public Server(int port, Connector connector) {
	this(port, 5000, Executors.newCachedThreadPool(), connector);
    }

    public Server(Connector connector) {
	this(8080, 5000, Executors.newCachedThreadPool(), connector);
    }

    public void start() throws Exception {
	System.out.println("Bright Server is shining!");
	ServerSocket serverSocket = this.serverSocket.load();
	while (!serverSocket.isClosed()) {
	    try {
		Socket socket = serverSocket.accept();
		socket.setSoTimeout(timeout);
		executor.execute(connector.plug(socket));
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }
	}
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
