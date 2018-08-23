package com.iprogrammerr.simpleserver;

import java.util.ArrayList;

import com.iprogrammerr.simpleserver.resolver.RequestsResolver;
import com.iprogrammerr.simpleserver.server.Server;

public class ServerApplication {

    public static void main(String[] args) {
	RequestsResolver.createInstance(new ArrayList<>());
	Server server = new Server(8080);
	server.start();
    }
}
