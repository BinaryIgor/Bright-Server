package com.iprogrammerr.bright.server.configuration;

public interface ServerConfiguration {

    String contextPath();

    int port();

    int timeout();

    Cors cors();
}
