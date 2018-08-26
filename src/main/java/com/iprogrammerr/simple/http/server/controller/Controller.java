package com.iprogrammerr.simple.http.server.controller;

import java.util.List;

import com.iprogrammerr.simple.http.server.resolver.RequestResolver;

public interface Controller {
    List<RequestResolver> getRequestResolvers();
}
