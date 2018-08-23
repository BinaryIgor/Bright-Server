package com.iprogrammerr.simpleserver.controller;

import java.util.List;

import com.iprogrammerr.simpleserver.resolver.RequestResolver;

public interface Controller {

    List<RequestResolver> getRequestResolvers();
}
