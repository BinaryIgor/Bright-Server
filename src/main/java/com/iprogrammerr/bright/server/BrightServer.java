package com.iprogrammerr.bright.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.iprogrammerr.bright.server.initialization.UnreliableStickyInitialization;

public final class BrightServer implements Server {

	private final int timeout;
	private final UnreliableStickyInitialization<ServerSocket> serverSocket;
	private final Executor executor;
	private final Connection connection;
	private boolean running;

	private BrightServer(UnreliableStickyInitialization<ServerSocket> serverSocket, int port, int timeout,
			Executor executor, Connection connection) {
		this.serverSocket = serverSocket;
		this.timeout = timeout;
		this.executor = executor;
		this.connection = connection;
	}

	public BrightServer(int port, int timeout, Executor executor, Connection connection) {
		this(new UnreliableStickyInitialization<>(() -> {
			return new ServerSocket(port);
		}), port, timeout, executor, connection);
	}

	public BrightServer(int port, int timeout, Connection connection) {
		this(port, timeout, Executors.newCachedThreadPool(), connection);
	}

	public BrightServer(int port, Connection connection) {
		this(port, 5000, Executors.newCachedThreadPool(), connection);
	}

	public BrightServer(Connection connection) {
		this(8080, 5000, Executors.newCachedThreadPool(), connection);
	}

	@Override
	public void start() throws Exception {
		if (this.running) {
			throw new Exception("Bright Server is running already!");
		}
		this.serverSocket.unstick();
		ServerSocket serverSocket = this.serverSocket.value();
		new Thread(() -> {
			System.out.println(String.format("Bright Server is shining on port %d!", serverSocket.getLocalPort()));
			this.running = true;
			while (!serverSocket.isClosed()) {
				try {
					Socket socket = serverSocket.accept();
					if (this.running) {
						socket.setSoTimeout(this.timeout);
						this.executor.execute(() -> this.connection.connect(socket));
					} else {
						serverSocket.close();
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

	@Override
	public void stop() {
		try {
			new Socket(this.serverSocket.value().getInetAddress(), this.serverSocket.value().getLocalPort()).close();
			this.running = false;
			System.out.println("Bright Server is fading away...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
