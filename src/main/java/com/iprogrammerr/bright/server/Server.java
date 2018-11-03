package com.iprogrammerr.bright.server;

public interface Server {

	void start() throws Exception;

	boolean isRunning();

	void stop();
}
