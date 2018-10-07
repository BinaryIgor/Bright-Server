package com.iprogrammerr.bright.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.iprogrammerr.bright.server.initialization.UnreliableInitialization;
import com.iprogrammerr.bright.server.initialization.UnreliableStickyInitialization;

public final class Server {

    private final int timeout;
    private final UnreliableInitialization<ServerSocket> serverSocket;
    private final Executor executor;
    private final Connection connection;

    public Server(UnreliableInitialization<ServerSocket> serverSocket, int port, int timeout, Executor executor,
	    Connection connection) {
	this.serverSocket = serverSocket;
	this.timeout = timeout;
	this.executor = executor;
	this.connection = connection;
    }

    public Server(int port, int timeout, Executor executor, Connection connection) {
	this(new UnreliableStickyInitialization<>(() -> {
	    return new ServerSocket(port);
	}), port, timeout, executor, connection);
    }

    public Server(int port, int timeout, Connection connection) {
	this(port, timeout, Executors.newCachedThreadPool(), connection);
    }

    public Server(int port, Connection connection) {
	this(port, 5000, Executors.newCachedThreadPool(), connection);
    }

    public Server(Connection connection) {
	this(8080, 5000, Executors.newCachedThreadPool(), connection);
    }

    public void start() throws Exception {
	System.out.println("Bright Server is shining!");
	ServerSocket serverSocket = this.serverSocket.value();
	while (!serverSocket.isClosed()) {
	    try {
		Socket socket = serverSocket.accept();
		socket.setSoTimeout(this.timeout);
		this.executor.execute(() -> this.connection.connect(socket));
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    public void stop() {
	try {
	    this.serverSocket.value().close();
	    System.out.println("Bright Server is fading away...");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
